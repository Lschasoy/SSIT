function [ img, imgYCbCr ] = convertRgb2ycbcr( imgPath )
%CONVERTHSV Lee la imagen especificada en imgPath y la transforma a YCbCr
img = imread(imgPath);
imgYCbCr = rgb2ycbcr(img);


end

