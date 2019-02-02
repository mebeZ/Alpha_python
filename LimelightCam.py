import numpy as np
import cv2
from networktables import NetworkTables

class LimelightCam():
    mid_x = 160 # x screen resolution / 2
    mid_y = 120 # y screen resolution / 2
    server = "http://10.11.55.11:5800/video"

    def __init__(self): #connects to limelight server
        print("Camera initiated")
        cap = cv2.VideoCapture(server)

    def determineleft(table, h): # Determines skew of contour
        if table.getNumber("ts" + str(h[1]),None) > -45:
            return (True)
        else:
            return False

    def ourindex3(tx0, tx1, tx2): # sorting contours by x if 3 contours found
        orig = [tx0, tx1, tx2]
        arr = [tx0, tx1, tx2]
        arr.sort()
        indices = [orig.index(arr[0]), orig.index(arr[1]),orig.index(arr[2])]
        return indices

    def ourindex2(tx0, tx1): # sorting contours by x position if 2 contours found
        orig = [tx0, tx1]
        arr = [tx0, tx1]
        arr.sort()
        indices = [orig.index(arr[0]), orig.index(arr[1])]
        return indices
    
    def getMarkedImage(): 
        ret, frame = cap.read()
        NetworkTables.initialize(server="10.11.55.2") # gets contours
        table = NetworkTables.getTable("limelight")
        tx0 = table.getNumber('tx0',None) 
        tx1 = table.getNumber('tx1',None) 
        tx2 = table.getNumber('tx2',None)
        if ((tx0 != None) and (tx1 != None)):
            if tx2 != 0:
                h = ourindex3(tx0,tx1,tx2) # Sorting
                if determineleft(table, h) == True:
                    x = (table.getNumber("tx" + str(h[0]),None) + table.getNumber("tx" + str(h[1]),None)) / 2
                    y = (table.getNumber("ty" + str(h[0]),None) + table.getNumber("ty" + str(h[1]),None)) / 2 # Averages x and y values together

                    x_coord = float(mid_x * (x + 1))
                    y_coord = 240 - float(mid_y * (y + 1)) # converts offset into tangible coordinates
                if determineleft(table, h) == False:
                    x = (table.getNumber("tx" + str(h[2]),None) + table.getNumber("tx" + str(h[1]),None)) / 2
                    y = (table.getNumber("ty" + str(h[2]),None) + table.getNumber("ty" + str(h[1]),None)) / 2
                    x_coord = float(mid_x * (x + 1))
                    y_coord = 240 - float(mid_y * (y + 1))
                cv2.line(frame, (int(x_coord)+15, int(y_coord)), (int(x_coord)-15, int(y_coord)), (255, 0, 0), thickness=3, lineType=8) # cross
                cv2.line(frame, (int(x_coord), int(y_coord)+15), (int(x_coord), int(y_coord)-15), (255, 0, 0), thickness=3, lineType=8)

            elif tx2 == 0: 
                h = ourindex2(tx0,tx1)                                                                                                                                                                                                                                              
                x = (table.getNumber("tx" + str(h[0]),None) + table.getNumber("tx" + str(h[1]),None)) / 2
                y = (table.getNumber("ty" + str(h[0]),None) + table.getNumber("ty" + str(h[1]),None)) / 2
                x_coord = float(mid_x * (x + 1))
                y_coord = 240 - float(mid_y * (y + 1))
                cv2.line(frame, (int(x_coord)+15, int(y_coord)), (int(x_coord)-15, int(y_coord)), (255, 0, 0), thickness=3, lineType=8)
                cv2.line(frame, (int(x_coord), int(y_coord)+15), (int(x_coord), int(y_coord)-15), (255, 0, 0), thickness=3, lineType=8)
        return frame

cam = LimelightCam()
cv2.imshow("chudnovksy x zawad", cam.getMarkedImage) # ;)
if cv2.waitKey(1)& 0xFF == ord('q'):
    break
cap.release()
cv2.destroyAllWindows()