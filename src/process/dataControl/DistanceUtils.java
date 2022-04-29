package process.dataControl;
public final class DistanceUtils {

    /**
     * ����뾶,��λ km
     */
    private static final double EARTH_RADIUS = 6378.137;

    /**
     * ���ݾ�γ�ȣ����������ľ���
     *
     * @param longitude1 ��һ����ľ���
     * @param latitude1  ��һ�����γ��
     * @param longitude2 �ڶ�����ľ���
     * @param latitude2  �ڶ������γ��
     * @return ���ؾ��� ��λǧ��
     */
    public final double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        // γ��
        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);
        // ����
        double lng1 = Math.toRadians(longitude1);
        double lng2 = Math.toRadians(longitude2);
        // γ��֮��
        double a = lat1 - lat2;
        // ����֮��
        double b = lng1 - lng2;
        // �����������Ĺ�ʽ
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(b / 2), 2)));
        // �����˵���뾶, ���ص�λ: ǧ��
        s =  s * EARTH_RADIUS;
        return s;
    }

}