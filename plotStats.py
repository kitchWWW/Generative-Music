import matplotlib.pyplot as plt
import json
import sys

fileName = 'out/'+sys.argv[1]+'/stats'

with open(fileName+'.csv') as f:
    content = f.readlines()
plt.plot(json.loads(content[0].split("\n")[0]))
plt.ylabel('Average Fitness');
plt.xlabel('Iterations');
plt.savefig(fileName+'.png')
