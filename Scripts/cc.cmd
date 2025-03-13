@echo off
python "%~dp0\create_class.py" %1
start Textpad %1.java