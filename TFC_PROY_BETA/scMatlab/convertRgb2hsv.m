function [ img, imgHsv ] = convertRgb2hsv( imgPath )
%CONVERTHSV Lee la imagen especificada en imgPath y la transforma a HSV
img = imread(imgPath);
imgHsv = rgb2hsv(img);
imgHsv = uint8(round(imgHsv * 255));


end

