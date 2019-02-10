import numpy as np
import cv2
from networktables import NetworkTables

class LimelightCam():
    def __init__(self): 
        NetworkTables.initialize(server="10.11.55.2") 
        self.cap   = cv2.VideoCapture("http://10.11.55.11:5800/video")
        self.mid_x = 160 # x screen resolution / 2
        self.mid_y = 120 # y screen resolution / 2
        self.res_x = 640 # Full x screen resolution including usb cam

    def determineLeft(self, table, h): 
        return table.getNumber("ts" + str(h),None) > -45

    def getIndexFromVal(self, orig, value):
        return orig.index(value)
    
    def sortIndices(self, txs):
        sortedIndices = []
        arr = txs.copy()
        arr.sort()
        for value in arr:
            if value is not None:
                index = self.getIndexFromVal(txs, value)
                sortedIndices.append(index)
        self.checkForTx2(sortedIndices)
        return sortedIndices

    def checkForTx2(self, indexList):
        if len(indexList) is not 3:
            indexList.append(0) # 0 is an arbitrary value. in this situation, tx2 will never return a false positive if it doesnt exist

    def getAverageCoords(self, table, sortedContours, index1, index2):
        x = (table.getNumber("tx" + str(sortedContours[index1]),None) + table.getNumber("tx" + str(sortedContours[index2]),None)) / 2
        y = (table.getNumber("ty" + str(sortedContours[index1]),None) + table.getNumber("ty" + str(sortedContours[index2]),None)) / 2

        x_coord = float(self.mid_x * (x+1))
        y_coord = 240 - float(self.mid_y * (y + 1))
        return x_coord, y_coord
    
    def getCrosshairs(self): 
        table = NetworkTables.getTable("limelight")
        tx0 = table.getNumber('tx0',None) 
        tx1 = table.getNumber('tx1',None) 
        tx2 = table.getNumber('tx2',None)

        if (tx0 and tx1):
            if(tx2):
                listOfContours = [tx0, tx1, tx2]
            else:
                listOfContours = [tx0, tx1]
                
            sortedContours = self.sortIndices(listOfContours)
            
            midContDirection   = self.determineLeft(table, sortedContours[1])
            leftContDirection  = self.determineLeft(table, sortedContours[0])
            rightContDirection = self.determineLeft(table, sortedContours[2])
            
            if (leftContDirection is not midContDirection): # hatches must have opposite tape face opposite side
                table.putBoolean("sawHatch", True)
                x_coord, y_coord = self.getAverageCoords(table, sortedContours, 0, 1)
                return x_coord, y_coord

            elif (rightContDirection is not midContDirection):
                table.putBoolean("sawHatch", True)
                x_coord, y_coord = self.getAverageCoords(table, sortedContours, 1, 2)
                return x_coord, y_coord
        else:
            return 0,0 # These are just placeholder coordinates that point to no hatch being found


    def drawCross(self, frame, x_coord, y_coord, lineLength):
        cv2.line(frame, (int(x_coord)+lineLength, int(y_coord)), (int(x_coord)-lineLength, int(y_coord)), (255, 0, 0), thickness=3, lineType=8)
        cv2.line(frame, (int(x_coord), int(y_coord)+lineLength), (int(x_coord), int(y_coord)-lineLength), (255, 0, 0), thickness=3, lineType=8)

    def encodeJPEG(self, image):
        ret, epic = cv2.imencode('.jpg', image, [int(cv2.IMWRITE_JPEG_QUALITY),90])
        return epic

    def getUSBImage(self):
        ret, frame = self.cap.read()
        return frame[0:240, 320:640]
        
    
    def getFrames(self):
        table = NetworkTables.getTable("limelight")
        x_val, y_val = self.getCrosshairs()
        frame   = self.getUSBImage()
        self.drawCross(frame, x_val, y_val, 15)
        jpegBytes = self.encodeJPEG(frame)
        return jpegBytes.tobytes()
    
    def checkForHatch(self):
        table = NetworkTables.getTable("limelight")
        return table.getBoolean("sawHatch", False)
