import numpy as np
import cv2
from networktables import NetworkTables
import socket
from client import Client

cap = cv2.VideoCapture("http://10.11.55.11:5800/video")

def ourindex3(tx0, tx1, tx2):
    orig = [tx0, tx1, tx2]
    arr = [tx0, tx1, tx2]
    arr.sort()
    indices = [orig.index(arr[0]), orig.index(arr[1]),orig.index(arr[2])]
    leftmost = table.getNumber('tx' + str(indices[0]),None)
    return indices

def ourindex2(tx0, tx1):
    orig = [tx0, tx1]
    arr = [tx0, tx1]
    arr.sort()
    indices = [orig.index(arr[0]), orig.index(arr[1])]
    leftmost = table.getNumber('tx' + str(indices[0]),None)
    return indices

def determineleft(h):
    if table.getNumber("ts" + str(h[1]),None) > -45:
        return (True)
    else:
        return False


while(True):
    # Capture frame-by-frame
    ret, frame = cap.read()
    NetworkTables.initialize(server="10.11.55.2")
    table = NetworkTables.getTable("limelight")
    LeftMost = []
    tx0 = table.getNumber('tx0',None) 
    tx1 = table.getNumber('tx1',None) 
    tx2 = table.getNumber('tx2',None)

    if (tx0 and tx1):
        
        if tx2 != 0:
            h = ourindex3(tx0,tx1,tx2)
            if determineleft(h) == True:
                avgpoint =  float[(table.getNumber(("tx" + str(h[0]),None) + table.getNumber("tx" + str(h[1]),None)) / 2),
                (table.getNumber(("ty" + str(h[0]),None) + table.getNumber("ty" + str(h[1]),None)) / 2)]
            if determineleft(h) == False:
                avgpoint =  float[(table.getNumber(("tx" + str(h[2]),None) + table.getNumber("tx" + str(h[1]),None)) / 2),
                (table.getNumber(("ty" + str(h[2]),None) + table.getNumber("ty" + str(h[1]),None)) / 2)]
            print(avgpoint)
        
        if tx2 == 0: 
            h = ourindex2(tx0,tx1)
            avgpoint =  float[(table.getNumber(("tx" + str(h[0]),None) + table.getNumber("tx" + str(h[1]),None)) / 2),
            (table.getNumber(("ty" + str(h[0]),None) + table.getNumber("ty" + str(h[1]),None)) / 2)]
            print(avgpoint)


    # Display the resulting frame
    cv2.imshow('frame',frame)
    if cv2.waitKey(1)& 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()

