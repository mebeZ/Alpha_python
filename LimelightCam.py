import numpy as np
import cv2
from networktables import NetworkTables

class LimelightCam():
    def __init__(self): #connects to limelight server
        self.cap = cv2.VideoCapture("http://limelight.local:5800/video")
        self.mid_x = 160 # x screen resolution / 2
        self.mid_y = 120 # y screen resolution / 2

    def determineleft(self, table, h): # Determines skew of contour
        if table.getNumber("ts" + str(h),None) > -45:
            return (True)
        else:
            return False

    def ourindex3(self, tx0, tx1, tx2): # sorting contours by x if 3 contours found
        orig = [tx0, tx1, tx2]
        arr = [tx0, tx1, tx2]
        arr.sort()
        indices = [orig.index(arr[0]), orig.index(arr[1]),orig.index(arr[2])]
        return indices

    def ourindex2(self, tx0, tx1): # sorting contours by x position if 2 contours found
        orig = [tx0, tx1]
        arr = [tx0, tx1]
        arr.sort()
        indices = [orig.index(arr[0]), orig.index(arr[1])]
        return indices
    
    def getCrosshairs(self): 
        NetworkTables.initialize(server="10.11.55.2") # gets contours
        table = NetworkTables.getTable("limelight")
        table.putNumber("camMode",0)
        ret, frame = self.cap.read()
        tx0 = table.getNumber('tx0',None) 
        tx1 = table.getNumber('tx1',None) 
        tx2 = table.getNumber('tx2',None)
        
        if ((tx0 != None) and (tx1 != None)):
            
            if tx2 != 0:
                h = self.ourindex3(tx0,tx1,tx2) # Sorting
                if (((self.determineleft(table, h[1])) and (self.determineleft(table, h[0]) == False)) or ((self.determineleft(table, h[1]) == False) and (self.determineleft(table, h[2])))):
                    table.putBoolean("sawHatch",True)
                else:
                    table.putBoolean("sawHatch",False)
                if self.determineleft(table, h[1]) == True:
                    x = (table.getNumber("tx" + str(h[0]),None) + table.getNumber("tx" + str(h[1]),None)) / 2
                    y = (table.getNumber("ty" + str(h[0]),None) + table.getNumber("ty" + str(h[1]),None)) / 2 # Averages x and y values together
                    x_coord = float(self.mid_x * (x + 1))
                    y_coord = 240 - float(self.mid_y * (y + 1)) # converts offset into tangible coordinates
                if self.determineleft(table, h[1]) == False:
                    x = (table.getNumber("tx" + str(h[2]),None) + table.getNumber("tx" + str(h[1]),None)) / 2
                    y = (table.getNumber("ty" + str(h[2]),None) + table.getNumber("ty" + str(h[1]),None)) / 2
                    x_coord = float(self.mid_x * (x + 1))
                    y_coord = 240 - float(self.mid_y * (y + 1))
                return x_coord, y_coord

            elif tx2 == 0:
                h = self.ourindex2(tx0,tx1)    
                if (self.determineleft(table, h[1])):
                    table.putBoolean("sawHatch",True)
                else:
                    table.putBoolean("sawHatch",False)                                                                                                                                                                                                                                          
                x = (table.getNumber("tx" + str(h[0]),None) + table.getNumber("tx" + str(h[1]),None)) / 2
                y = (table.getNumber("ty" + str(h[0]),None) + table.getNumber("ty" + str(h[1]),None)) / 2
                x_coord = float(self.mid_x * (x + 1))
                y_coord = 240 - float(self.mid_y * (y + 1))
                return x_coord, y_coord
        else:
            #cv2.imshow("frame", frame)
            return 0,0
    
    def getFrames(self):
        NetworkTables.initialize(server="10.11.55.2") 
        table = NetworkTables.getTable("limelight")
        x_coord, y_coord = self.getCrosshairs()
        table.putNumber("camMode",1)
        ret, frame = self.cap.read()
        cv2.line(frame, (int(x_coord)+15, int(y_coord)), (int(x_coord)-15, int(y_coord)), (255, 0, 0), thickness=3, lineType=8)
        cv2.line(frame, (int(x_coord), int(y_coord)+15), (int(x_coord), int(y_coord)-15), (255, 0, 0), thickness=3, lineType=8)
        ret, epic = cv2.imencode('.jpg', frame, [int(cv2.IMWRITE_JPEG_QUALITY),90])
        return epic.tobytes()

    def checkForHatch(self):
        NetworkTables.initialize(server="10.11.55.2") 
        table = NetworkTables.getTable("limelight")
        return table.getBoolean("sawHatch", False)
    
