package com.jianda.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 转换工具类
 */
public class ConvertUtils {

    /**
     * 数据类
     */
    public static class DataValue {

        public static String nullOfString(String str) {
            if (str == null) {
                str = "";
            }
            return str;
        }

        public static byte stringToByte(String str) {
            byte b = 0;
            if (str != null) {
                try {
                    b = Byte.parseByte(str);
                } catch (Exception e) {

                }
            }
            return b;
        }

        public static boolean stringToBoolean(String str) {
            if (str == null) {
                return false;
            } else {
                if (str.equals("1")) {
                    return true;
                } else if (str.equals("0")) {
                    return false;
                } else {
                    try {
                        return Boolean.parseBoolean(str);
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
        }

        public static int stringToInt(String str) {
            int i = 0;
            if (str != null) {
                try {
                    i = Integer.parseInt(str.trim());
                } catch (Exception e) {
                    i = 0;
                }

            } else {
                i = 0;
            }
            return i;
        }

        public static short stringToShort(String str) {
            short i = 0;
            if (str != null) {
                try {
                    i = Short.parseShort(str.trim());
                } catch (Exception e) {
                    i = 0;
                }
            } else {
                i = 0;
            }
            return i;
        }

        public static double stringToDouble(String str) {
            double i = 0;
            if (str != null) {
                try {
                    i = Double.parseDouble(str.trim());
                } catch (Exception e) {
                    i = 0;
                }
            } else {
                i = 0;
            }
            return i;
        }

        public static String intToString(int i) {
            String str = "";
            try {
                str = String.valueOf(i);
            } catch (Exception e) {
                str = "";
            }
            return str;
        }


        public static long doubleToLong(double d) {
            long lo = 0;
            try {
                //double转换成long前要过滤掉double类型小数点后数据
                lo = Long.parseLong(String.valueOf(d).substring(0, String.valueOf(d).lastIndexOf(".")));
            } catch (Exception e) {
                lo = 0;
            }
            return lo;
        }

        public static int doubleToInt(double d) {
            int i = 0;
            try {
                //double转换成long前要过滤掉double类型小数点后数据
                i = Integer.parseInt(String.valueOf(d).substring(0, String.valueOf(d).lastIndexOf(".")));
            } catch (Exception e) {
                i = 0;
            }
            return i;
        }

        public static double longToDouble(long d) {
            double lo = 0;
            try {
                lo = Double.parseDouble(String.valueOf(d));
            } catch (Exception e) {
                lo = 0;
            }
            return lo;
        }

        public static int longToInt(long d) {
            int lo = 0;
            try {
                lo = Integer.parseInt(String.valueOf(d));
            } catch (Exception e) {
                lo = 0;
            }
            return lo;
        }

        public static long stringToLong(String str) {
            Long li = new Long(0);
            try {
                li = Long.valueOf(str);
            } catch (Exception e) {
                //li = new Long(0);
            }
            return li.longValue();
        }

        public static String longToString(long li) {
            String str = "";
            try {
                str = String.valueOf(li);
            } catch (Exception e) {

            }
            return str;
        }
    }

    /**
     * 时间日期类
     * Created by admin on 2017/10/26.
     */
    public static class DateValue {
        private final String DATA_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
        private final String DATA_FORMAT_2 = "yyyy-MM-dd";
        private final String DATA_FORMAT_3 = "HH:mm:ss";

        //        获取系统时间
        public static String getStringDate(String dateFormat) {
            Date currentTime = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            return formatter.format(currentTime);
        }
    }
}
