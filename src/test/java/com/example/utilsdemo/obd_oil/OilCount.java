package com.example.utilsdemo.obd_oil;

public class OilCount {

    public static void main(String[] args) {
        Double oil = getOilCount(801, 102, 18, 1, 2);
        System.out.println(oil);
    }

    /*
    根据理想气体定律 PV=mrt，可得 m=PV/rt。再根据速度-密度方程，气体流量 m=PV1u/rt（单
    位是摩尔 mol）
    P 为绝对压力，V 气体体积，m 气体质量，r 空气气体常数，t 绝对温度,V1 气缸工作容积，
    越等于汽车排量，单位是 m3 。
    进入气缸的空气质量 M=29*m。
    发动机曲轴每转两周完成一次循环，因此空气质量（单位 g/MIN）
    A=0.5*M*n=14.5*m*n=14.5*P*V1*u*n/(r*t)
    根据理论空燃比，消耗的汽油质量为（单位 g/MIN）
    G=A/λ =14.5*P*V1*u*n /(r*t*λ )
    根据汽油密度，算法汽油体积（单位 ml/MIN）
    V1=G/ρ =14.5*1000*P*V1*u*n /(r*t*λ *735)
    转换为 L/MIN
    V2=14.5*P*V1*u*n /(r*t*λ *735)
    转换为 L/H（升每小时），这个单位正好是怠速时候的瞬时油耗
    V=60*14.5*P*V1*u*n /(r*t*λ *735)
    把上面的数字带入公式得到(我自己的车是 2.0L 排量自然吸气，也就是 0.002)
    V=60*14.5*P*0.002*0.8*n /(8.31*(IAT+273)*14.7*735)
    = 0.01549617602*RPM*MAP/(IAT+233)
    根据车速 VSS，行驶瞬时油耗等于
    V/VSS*100=1.549617602*RPM*MAP/(IAT+233)/VSS;
     */
    public static Double getOilCount(double RPM, double MAP, double IAT, double VSS, double V) {
        return (1.549617602 * (V / 2)) * RPM * MAP / (IAT + 233) / VSS;
    }

}
