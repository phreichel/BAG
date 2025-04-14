import csv
import os
import time
from datetime import datetime, timedelta
import threading

CSV_FILE = "tasks.csv"
STATE_FILE = "state.txt"
TASK_FIELDS = ["TASKID", "TASKNAME", "TASKTYP", "STARTZEIT", "STOPZEIT"]

task_id = 1
current_task = None
task_stack = []
last_input_time = datetime.now()
check_interval = timedelta(minutes=30)
lock = threading.Lock()

def load_last_state():
    global task_id, current_task, task_stack
    if os.path.exists(CSV_FILE):
        with open(CSV_FILE, "r", newline="", encoding="utf-8") as f:
            reader = csv.DictReader(f)
            rows = list(reader)
            if rows:
                task_id = int(rows[-1]["TASKID"]) + 1

    if os.path.exists(STATE_FILE):
        with open(STATE_FILE, "r", encoding="utf-8") as f:
            lines = f.read().splitlines()
            if lines:
                task_stack = eval(lines[0])
                current_task = task_stack[-1] if task_stack else None

def save_state():
    with open(STATE_FILE, "w", encoding="utf-8") as f:
        f.write(str(task_stack))

def save_task_to_csv(task):
    exists = os.path.isfile(CSV_FILE)
    with open(CSV_FILE, "a", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=TASK_FIELDS)
        if not exists:
            writer.writeheader()
        writer.writerow(task)

def end_current_task():
    global current_task
    if current_task:
        current_task["STOPZEIT"] = datetime.now().isoformat()
        save_task_to_csv(current_task)
        current_task = None

def start_task(name="Task", typ="normal"):
    global task_id, current_task
    end_current_task()
    current_task = {
        "TASKID": task_id,
        "TASKNAME": name,
        "TASKTYP": typ,
        "STARTZEIT": datetime.now().isoformat(),
        "STOPZEIT": ""
    }
    task_id += 1
    task_stack.append(current_task)
    save_state()
    print_status()

def handle_resume():
    global current_task
    end_current_task()
    for prev in reversed(task_stack[:-1]):
        if prev["TASKTYP"] == "normal":
            start_task(name=prev["TASKNAME"], typ="normal")
            break

def print_status():
    if current_task:
        print(f"Aktuell: {current_task['TASKTYP'].upper()} - {current_task['TASKNAME']} (seit {current_task['STARTZEIT']})")
    else:
        print("Kein aktiver Task.")

def reminder_loop():
    global last_input_time
    while True:
        time.sleep(60)
        with lock:
            if datetime.now() - last_input_time > check_interval:
                print("\nArbeitest du noch am aktuellen Task? (Eingabe für Bestätigung oder Änderung)")
                last_input_time = datetime.now()

def main():
    global last_input_time
    load_last_state()
    print_status()
    threading.Thread(target=reminder_loop, daemon=True).start()

    while True:
        try:
            user_input = input("> ").strip()
            with lock:
                last_input_time = datetime.now()

                if user_input.startswith("t"):
                    parts = user_input.split(" ", 1)
                    name = parts[1] if len(parts) > 1 else f"Task {task_id}"
                    start_task(name=name, typ="normal")

                elif user_input.startswith("i"):
                    parts = user_input.split(" ", 1)
                    name = parts[1] if len(parts) > 1 else f"Interrupt {task_id}"
                    start_task(name=name, typ="interrupt")

                elif user_input == "r":
                    handle_resume()

                else:
                    print("Unbekannter Befehl. Nutze 't', 't <name>', 'i', 'i <name>' oder 'r'.")
        except KeyboardInterrupt:
            print("\nBeende Programm. Fortschritt wird gespeichert.")
            end_current_task()
            save_state()
            break

if __name__ == "__main__":
    main()
