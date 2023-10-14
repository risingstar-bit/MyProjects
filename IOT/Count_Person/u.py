import machine
import utime
# Define the GPIO pins
TRIGGER_PIN_1 = 0
ECHO_PIN_1 = 1
TRIGGER_PIN_2 = 2
ECHO_PIN_2 = 3
A=False
B=False
str=""
count=0
sl_no=0
s1="Entered"
s2="Exits"
def measure_distance(trigger_pin, echo_pin):

    trigger = machine.Pin(trigger_pin, machine.Pin.OUT)
    echo = machine.Pin(echo_pin, machine.Pin.IN)

    trigger.value(0)
    utime.sleep_us(2)
    trigger.value(1)
    utime.sleep_us(10)
    trigger.value(0)

    pulse_duration = machine.time_pulse_us(echo, 1)
    distance = pulse_duration / 58.0
    return distance
# Main program
with open('Output.xls', 'w') as file:
    # Write header row
    file.write("SL NO. , Sensor1(cm), Sensor2(cm), Activity\n")
    while True:
    # Measure distance from the first sensor
        distance_1 = measure_distance(TRIGGER_PIN_1, ECHO_PIN_1)
        distance_2 = measure_distance(TRIGGER_PIN_2, ECHO_PIN_2)
        sl_no+=1
        file.write(f"{sl_no},{distance_1},{distance_2}\n")
        if(distance_1<10):
            A=True
            str=str+"A"
            A=False
        if(distance_2<10):
            B=True
            str=str+"B"
            B=False
        if(len(str)>1):
            for i in range(1,len(str)):
                if(str[i]>str[i-1]):
                    print("1 Person Entered in the room")
                    sl_no+=1
                    file.write(f"{sl_no},{distance_1},{distance_2},{s1}\n")
                    count+=1
                    str=""
                    break
                if(str[i-1]>str[i]):
                    print("1 Person Exits from the room")
                    str=""
                    count-=1
                    sl_no+=1
                    file.write(f"{sl_no},{distance_1},{distance_2},{s2}\n")
                    break
        if count>0:
            print("Total Peroson in the room : ", count)
        else:
            print("Total Peroson in the room : ", 0)
        utime.sleep(1)
