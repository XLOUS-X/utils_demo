package com.example.utilsdemo.other;

public class GISDemo {

    public static void main(String[] args) {

        double lon = 0;
        double lat = 0;

        int mapWidth = 991;
        int mapHeight = 768;

        double mapLonLeft = -180;
        double mapLonRight = 180;
        double mapLonDelta = mapLonRight - mapLonLeft;

        double mapLatBottom = -85.05112878;
        double mapLatBottomDegree = mapLatBottom * Math.PI / 180;
        double worldMapWidth = ((mapWidth / mapLonDelta) * 360) / (2 * Math.PI);
        double mapOffsetY = (worldMapWidth / 2 * Math.log((1 + Math.sin(mapLatBottomDegree)) / (1 - Math.sin(mapLatBottomDegree))));

        double x = (lon - mapLonLeft) * (mapWidth / mapLonDelta);
        double y = 0.1;
        if (lat < 0) {
            lat = lat * Math.PI / 180;
            y = mapHeight - ((worldMapWidth / 2 * Math.log((1 + Math.sin(lat)) / (1 - Math.sin(lat)))) - mapOffsetY);
        } else if (lat > 0) {
            lat = lat * Math.PI / 180;
            lat = lat * -1;
            y = mapHeight - ((worldMapWidth / 2 * Math.log((1 + Math.sin(lat)) / (1 - Math.sin(lat)))) - mapOffsetY);
            System.out.println("y before minus: " + y);
            y = mapHeight - y;
        } else {
            y = mapHeight / 2;
        }
        System.out.println(x);
        System.out.println(y);

    }


    public static double[] lngLat2Mercator(double lng, double lat) {
        double[] xy = new double[2];
        double x = lng * 20037508.342789 / 180;
        double y = Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180);
        y = y * 20037508.34789 / 180;
        xy[0] = x;
        xy[1] = y;
        return xy;
    }

    public static double[] mercator2LngLat(double mercatorX, double mercatorY) {
        double[] xy = new double[2];
        double x = mercatorX / 20037508.34 * 180;
        double y = mercatorY / 20037508.34 * 180;
        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
        xy[0] = x;
        xy[1] = y;
        return xy;
    }
}
