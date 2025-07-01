import os;
import json;

path = os.path.abspath('.')
print(path)
file = os.path.join(path, 'NUMBER.JS')

while (not os.path.exists(file) and path!=os.path.dirname(path)):
	path = os.path.dirname(path)
	print(path)
	file = os.path.join(path, 'NUMBER.JS')

if (not os.path.exists(file)):
	print('FOUND NO NUMBER CONFIG')
	exit()

print('NUMBER CONFIG FOUND: ' + file)

with open(file, 'r') as handle:
	cfg = json.load(handle)

prefix  = cfg['PREFIX']
postfix = cfg['POSTFIX']
counter   = cfg['COUNTER']
length  = cfg['LENGTH']

cfg['COUNTER'] = counter+1
with open(file, "w") as handle:
    json.dump(cfg, handle, indent=4)

number = '' + str(counter)
while len(number) < length:
	number = '0' + number

newfile = prefix + number + postfix
print('WRITING NEW FILE: ' + newfile)

with open(newfile, "w") as handle:
    handle.write('')
