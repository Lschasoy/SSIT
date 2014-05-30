function [ img, imgLab ] = convertRgb2lab( imgPath )
%CONVERTHSV Lee la imagen especificada en imgPath y la transforma a LAB
img = imread(imgPath);
cform = makecform('srgb2lab');
imgLab = applycform(img, cform);


end

