package com.example.utilsdemo.other;

import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

public class Proj4jTest {

    static final String WGS84_PARAM = "+title=long/lat:WGS84 +proj=longlat +datum=WGS84 +units=degrees";
    CoordinateReferenceSystem WGS84 = crsFactory.createFromParameters("WGS84",
            WGS84_PARAM);

    private static final CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
    private static final CRSFactory crsFactory = new CRSFactory();

    private static CoordinateReferenceSystem createCRS(String crsSpec) {
        CoordinateReferenceSystem crs = null;
        // test if name is a PROJ4 spec
        if (crsSpec.indexOf("+") >= 0 || crsSpec.indexOf("=") >= 0) {
            crs = crsFactory.createFromParameters("Anon", crsSpec);
        } else {
            crs = crsFactory.createFromName(crsSpec);
        }
        // crs = crsFactory.createFromParameters("Anon", crsSpec);

        return crs;
    }

    public static void main(String[] args) {
        // new CoordinateTransformTester(true).checkTransform("EPSG:4269",
        // 117.19625, 31.83879,
        // "+proj=tmerc +lat_0=0 +lon_0=117 +y_0=0 +x_0=500000 +k=0.9996 +pm=0
        // +zone=50 +to_meter=1 +a=6378137 +rf=298.257223563 +nodefs",
        // 1640416.667, 916074.825, 0.1);


//		lonlat2m();
        m2lonlat();
    }

    private static void lonlat2m() {
        System.out.println("....");

        // 117.19625 31.83879 518568.9 3522583.9
        double x1 = 117.19625d;
        double y1 = 31.83879d;
        // double x2 = 518568.9d;
        // double y2 = 3522583.9d;

        // String srcCRS = "EPSG:4269";
        String WGS84_PARAM = "+title=long/lat:WGS84 +proj=longlat +datum=WGS84 +units=degrees";
        String tgtCRS = "+proj=tmerc +lat_0=0 +lon_0=117 +y_0=0 +x_0=500000 +k=0.9996 +zone=50 +to_meter=1 +a=6378137 +ellps=WGS84 +units=m +no_defs";

        CoordinateTransform trans = ctFactory
                .createTransform(createCRS(WGS84_PARAM), createCRS(tgtCRS));
        ProjCoordinate pout = new ProjCoordinate();

        ProjCoordinate p = new ProjCoordinate(x1, y1);

        trans.transform(p, pout);

        System.out.println(p.x);
        System.out.println(p.y);
        System.out.println(pout.x);
        System.out.println(pout.y);

        System.out.println("-------		// 117.19625 31.83879 518568.9 3522583.9 ");
        p = new ProjCoordinate(y1, x1);
        trans.transform(p, pout);

//		System.out.println(p.x);
//		System.out.println(p.y);
//		System.out.println(pout.x);
//		System.out.println(pout.y);
    }



    private static void m2lonlat() {
        System.out.println("....");

        // 117.19625 31.83879 518568.9 3522583.9
//		double x2 = 117.19625d;
//		double y2 = 31.83879d;
        double x1 = 518568.9d;
        double y1 = 3522583.9d;

        // String srcCRS = "EPSG:4269";
        String WGS84_PARAM = "+title=long/lat:WGS84 +proj=longlat +datum=WGS84 +units=degrees";
        String tgtCRS = "+proj=tmerc +lat_0=0 +lon_0=117 +y_0=0 +x_0=500000 +k=0.9996 +zone=50 +to_meter=1 +a=6378137 +ellps=WGS84 +units=m +no_defs";

        CoordinateTransform trans = ctFactory
                .createTransform( createCRS(tgtCRS),createCRS(WGS84_PARAM));
        ProjCoordinate pout = new ProjCoordinate();

        ProjCoordinate p = new ProjCoordinate(x1, y1);

        trans.transform(p, pout);

        System.out.println(p.x);
        System.out.println(p.y);
        System.out.println(pout.x);
        System.out.println(pout.y);

        System.out.println("-------		// 117.19625 31.83879 518568.9 3522583.9 ");
        p = new ProjCoordinate(y1, x1);
        trans.transform(p, pout);

//		System.out.println(p.x);
//		System.out.println(p.y);
//		System.out.println(pout.x);
//		System.out.println(pout.y);
    }
}
