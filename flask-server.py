from flask import Flask, render_template, Response
import cv2
from LimelightCam import LimelightCam
app = Flask(__name__)

@app.route('/')
def index():
    return Response(gen(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/stream.mjpg')
def stream():
    return Response(gen(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')


def gen():
    while(True):
        camera = LimelightCam()
        frame = camera.getFrames()
        yield (b'--frame\r\n'
           b'Content-Type: image/jpeg\r\n\r\n' + frame + b'll\r\n')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5802, debug=True)
