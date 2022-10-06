ffmpeg -framerate 60 -i img%%04d.bmp -c:v libx264 -pix_fmt yuv444p -crf 15 output.mp4
pause