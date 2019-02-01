import numpy as np
import cv2
from networktables import NetworkTables
import socket

cap = cv2.VideoCapture("http://10.11.55.11:5800/video")
mid_x = 160
mid_y = 120

def ourindex3(tx0, tx1, tx2):
    orig = [tx0, tx1, tx2]
    arr = [tx0, tx1, tx2]
    arr.sort()
    indices = [orig.index(arr[0]), orig.index(arr[1]),orig.index(arr[2])]
    return indices

def ourindex2(tx0, tx1):
    orig = [tx0, tx1]
    arr = [tx0, tx1]
    arr.sort()
    indices = [orig.index(arr[0]), orig.index(arr[1])]
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

    if ((tx0 != None) and (tx1 != None)):
        if tx2 != 0:
            h = ourindex3(tx0,tx1,tx2)
            if determineleft(h) == True:
                x = (table.getNumber("tx" + str(h[0]),None) + table.getNumber("tx" + str(h[1]),None)) / 2
                y = (table.getNumber("ty" + str(h[0]),None) + table.getNumber("ty" + str(h[1]),None)) / 2

                x_coord = float(mid_x * (x + 1))
                y_coord = 240 - float(mid_y * (y + 1))
            if determineleft(h) == False:
                x = (table.getNumber("tx" + str(h[2]),None) + table.getNumber("tx" + str(h[1]),None)) / 2
                y = (table.getNumber("ty" + str(h[2]),None) + table.getNumber("ty" + str(h[1]),None)) / 2
                x_coord = float(mid_x * (x + 1))
                y_coord = 240 - float(mid_y * (y + 1))

            print("X:" + str(x_coord) +  " Y:" + str(y_coord))
            cv2.line(frame, (int(x_coord)+15, int(y_coord)), (int(x_coord)-15, int(y_coord)), (255, 0, 0), thickness=3, lineType=8)
            cv2.line(frame, (int(x_coord), int(y_coord)+15), (int(x_coord), int(y_coord)-15), (255, 0, 0), thickness=3, lineType=8)

        elif tx2 == 0: 
            h = ourindex2(tx0,tx1)                                                                                                                                                                                                                                              
            x = (table.getNumber("tx" + str(h[0]),None) + table.getNumber("tx" + str(h[1]),None)) / 2
            y = (table.getNumber("ty" + str(h[0]),None) + table.getNumber("ty" + str(h[1]),None)) / 2
            x_coord = float(mid_x * (x + 1))
            y_coord = 240 - float(mid_y * (y + 1))
            cv2.line(frame, (int(x_coord)+15, int(y_coord)), (int(x_coord)-15, int(y_coord)), (255, 0, 0), thickness=3, lineType=8)
            cv2.line(frame, (int(x_coord), int(y_coord)+15), (int(x_coord), int(y_coord)-15), (255, 0, 0), thickness=3, lineType=8)
            print("X:" + str(x_coord) +  " Y:" + str(y_coord))


    # Display the resulting frame
    cv2.imshow('frame',frame)
    cv2.imwrite("image.jpg", frame)
    if cv2.waitKey(1)& 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()
