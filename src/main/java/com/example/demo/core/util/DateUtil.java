package com.example.demo.core.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *ClassName: 时间工具类 
 * @Description: TODO
 * @author jensen
 * @date 2015年9月16日 下午8:05:21
 */
public class DateUtil {
	

	private final static SimpleDateFormat shortSdf= new SimpleDateFormat("yyyy-MM-dd");;
    private final static SimpleDateFormat longHourSdf  =new SimpleDateFormat("yyyy-MM-dd HH");
    private final static SimpleDateFormat longSdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * @Description: 获取所有时间字符串格式
	 * @return   
	 * @return String[]  
	 * @throws
	 * @author jensen
	 * @date 2016年4月9日 下午3:59:34
	 */
	public static String[] getDatePatterns(){
		return new String[]{
				"yyyy-MM-dd HH:mm:ss:ssss",
				"yyyy-MM-dd HH:mm:ss",
				"yyyy-MM-dd HH:mm",
				"yyyy/MM/dd HH:mm",
				"yyyy/m/d h:mm",
				"yyyy-MM-dd HH",
				"yyyy-MM-dd",
				"yyyy/MM/dd",
				"yyyy/m/d",
				"yy/m/d",
				"m/d/yy",
				"MM/dd/yyyy"
		};
	}
	
	/**
	 * @Description: 字符串转换为日期格式
	 * @param dateValue
	 * @return   
	 * @return Date  
	 * @throws
	 * @author jensen
	 * @date 2016年4月9日 下午4:00:58
	 */
	public static Date parseDate(String dateValue) {
		final String localDateFormats[] = DateUtil.getDatePatterns();
        String v = dateValue;
        if (v.length() > 1 && v.startsWith("'") && v.endsWith("'")) {
            v = v.substring (1, v.length() - 1);
        }

        for (final String dateFormat : localDateFormats) {
        	
            final SimpleDateFormat dateParser = new SimpleDateFormat(dateFormat);
            final ParsePosition pos = new ParsePosition(0);
            final Date result = dateParser.parse(v, pos);
            if (pos.getIndex() != 0) {
                return result;
            }
        }
        return null;
	}
	
	/**
	 * @Description: 获取之前几天的时间
	 * @param d   时间
	 * @param day 天数
	 * @return Date  
	 * @throws
	 * @author jensen
	 * @date 2015年9月16日 下午8:05:53
	 */
	public static Date getDateBefore(Date d, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
		return calendar.getTime();
	}

	/**
	 * @Description: 获取之后几天的时间
	 * @param d    时间
	 * @param day  天数
	 * @return Date  
	 * @throws
	 * @author jensen
	 * @date 2015年9月16日 下午8:06:32
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
		return calendar.getTime();
	}
	

	/**
	 * 比较时间是否在某个时间范围内
	 * 
	 * @param stim
	 *            范围起始时间
	 * @param etim
	 *            范围结束时间
	 * @param ztim
	 *            要判断比较的时间
	 * @return
	 * @throws ParseException
	 */
	/*public static int comparedate(String stim, String etim, String ztim)
			throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long startM = sdf.parse(stim).getTime();
		Long endM = sdf.parse(etim).getTime();
		Long ztimM = sdf.parse(ztim).getTime();
		long result1 = ztimM - startM;
		long result2 = ztimM - endM;

		if (result1 >= 0 && result2 <= 0) {
			return 1; // 在范围内
		} else {
			return 0; // 不在范围内
		}

	}*/
	
	//比较时间超过多少年多少日多少天
	public static int comparedatey(String onetim, String nowtime,int year,String format)
			throws ParseException {
		
        String twotim = strBeforetimeYear(nowtime,year,format);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Long startM = sdf.parse(onetim).getTime();
		Long endM = sdf.parse(twotim).getTime();
		long result1 = endM - startM;

		if (result1 > 0) {
			return 0; // 超出
		} else {
			return 1; // 未超出
		}

	}
	/**
	 * 0超过 age岁
	 * 1未超 age岁
	 * @param idnum
	 * @return
	 */
	public static int getIdage(String idnum,int age,String format){
        StringBuilder sb=new StringBuilder(idnum);
        sb.insert(4,"-");
        sb.insert(7, "-");
        String idage = sb.toString();
        
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        String tday = formatter.format(new Date());
        
        int isnum = 0;
        try {
        	isnum = DateUtil.comparedatey(idage,tday,age,format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return isnum;
	}
	

	/**
	 * 得到开始时间与结束时间之间的每一天（格式为yyyyMMdd HH:mm:ss 的字符串） long result 开始时间与结束时间之间相差的天数
	 * StringBuffer sbsb 将每一个循环遍历的时间拼接成yyyyMMdd HH:mm:ss 的字符串格式 List<String>
	 * tlist 存放yyyyMMdd HH:mm:ss 字符串格式时间的集合 startime 开始时间 (格式yyyy-MM-dd) endtime
	 * 结束时间 (格式yyyy-MM-dd)
	 * */
	public static LinkedList<String> getdiffertimes(String startime, String endtime,String format) {
		// 计算相差天数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Long startM = (long) 0;
		try {
			startM = sdf.parse(startime).getTime();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Long endM = (long) 0;
		try {
			endM = sdf.parse(endtime).getTime();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		long result = (endM - startM) / (24 * 60 * 60 * 1000);
		System.out.println("差:" + result + "天");
		// 以字符串形式循环得到每一天
		Date startDate = null;
		try {
			startDate = new SimpleDateFormat(format)
					.parse(startime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar startTime = Calendar.getInstance();
		startTime.clear();
		startTime.setTime(startDate);
		StringBuffer sb = null;
		// 存放始发时间的集合
		LinkedList<String> tlist = new LinkedList<String>();
		for (int i = 0; i <= (int) result; i++) {
			sb = new StringBuffer();

			String strtime = String.valueOf(startTime.get(Calendar.YEAR));

			sb.append(strtime);
			
			sb.append("-");

			if ((startTime.get(Calendar.MONTH) + 1) < 10) {
				sb.append("0").append((startTime.get(Calendar.MONTH) + 1));
			} else {
				sb.append((startTime.get(Calendar.MONTH) + 1));
			}

			sb.append("-");
			
			if (startTime.get(Calendar.DAY_OF_MONTH) < 10) {
				sb.append("0").append(startTime.get(Calendar.DAY_OF_MONTH));
			} else {
				sb.append(startTime.get(Calendar.DAY_OF_MONTH));
			}
//			sb.append(" 00:00:00");

			tlist.add(sb.toString());

			startTime.add(Calendar.DAY_OF_YEAR, 1);
		}
//		System.out.println(startime + "与" + endtime + "之间的每一天:");
//		for (int i = 0; i < tlist.size(); i++) {
//			System.out.println(tlist.get(i));
//		}
		return tlist;
	}

	/**
	 * 将2014-03-05 转换为 05Mar14格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formdateEn(String date,String format) {

		Date trfrodate = null; // 05Mar14 格式日期
		try {
			trfrodate = new SimpleDateFormat(format)
					.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String datef = String.valueOf(trfrodate);

		return datef.split(" ")[2] + datef.split(" ")[1]
				+ datef.substring(datef.length() - 2);

	}

	/**
	 * 获取字符串时间的前几天 并以字符串形式返回
	 * 
	 * @param time
	 * @param day
	 * @return
	 */
	public static String strBeforetime(String time, int day,String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format)
					.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Date date = new Date();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		if (day > 0) {
			calendar.add(calendar.DATE, -day);
		}else{
			calendar.add(calendar.MINUTE, -30);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(
				format);
		String datetime = sdf.format(calendar.getTime());
		return datetime;

	}
	/**
	 * 获取字符串时间的后几天 并以字符串形式返回
	 * 
	 * @param time
	 * @param day
	 * @return
	 */
	public static String strAftertime(String time, int day,String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format)
					.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Date date = new Date();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		if (day > 0) {
			calendar.add(calendar.DATE, +day);
		}else{
			calendar.add(calendar.MINUTE, +30);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(
				format);
		String datetime = sdf.format(calendar.getTime());
		return datetime;

	}
	
	
	/**
	 * 获取当前系统时间的后几秒
	 * @param seconds
	 * @return
	 */
	public static Date getRetimeSeconds(int seconds){
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.add(calendar.SECOND, seconds);
	    return calendar.getTime();
		
	}
	
	/**
	 * 获取当前系统时间的后几天
	 * @param time
	 * @param day
	 * @return
	 */
	public static String getRetime(int day,String format){
		 String str = new SimpleDateFormat(format).format(new Date()); 
		 String noe = DateUtil.strAftertime(str, day,format);
		 String retime = noe.split(" ")[0]+" 00:00:00";
		 return retime;
	}
	
	/**
	 * 获取字符串时间的前几年 并以字符串形式返回
	 * 
	 * @param time
	 * @param day
	 * @return
	 */
	public static String strBeforetimeYear(String time, int year,String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format)
					.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Date date = new Date();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		if (year >= 0) {
			calendar.add(calendar.YEAR, -year);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(
				format);
		String datetime = sdf.format(calendar.getTime());
		return datetime;

	}
	

	/**
	 * 将字符串类型转换为时间类型
	 * 
	 * @return
	 */
	public static Date str2Date(String str,String format) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			d = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}


	/**
	 * 将时间格式化
	 */
	public static Date DatePattern(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			String dd = sdf.format(date);
			date = str2Date(dd, pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @Description: 将时间转换成字符串
	 * @param date
	 * @param format
	 * @return   
	 * @return String  
	 * @throws
	 * @author lizs
	 * @date 2016年5月31日 下午6:05:12
	 */
	public static String date2Str(Date date,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取昨天
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastDate(Date date,String format) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);

		calendar.add(calendar.DATE, -1);

		return str2Date(date2Str(calendar.getTime(),format),format);
	}

	/**
	 * 获取前几天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeforeDate(Date date, int dates,String format) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);

		calendar.add(calendar.DATE, -dates);

		return str2Date(date2Str(calendar.getTime(),format),format);
	}

	/**
	 * 获取后几天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeyangDate(Date date, int dates,String format) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);

		calendar.add(calendar.DATE, +dates);

		return str2Date(date2Str(calendar.getTime(),format),format);
	}

	/**
	 * 获取上周第一天（周一）
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getLastWeekStart(Date date,String format) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		int i = calendar.get(calendar.DAY_OF_WEEK) - 1;
		int startnum = 0;
		if (i == 0) {
			startnum = 7 + 6;
		} else {
			startnum = 7 + i - 1;
		}
		calendar.add(calendar.DATE, -startnum);

		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	/**
	 * 获取上周最后一天（周末）
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getLastWeekEnd(Date date,String format) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		int i = calendar.get(calendar.DAY_OF_WEEK) - 1;
		int endnum = 0;
		if (i == 0) {
			endnum = 7;
		} else {
			endnum = i;
		}
		//calendar.add(calendar.DATE, -(endnum - 1));
		calendar.add(calendar.DATE, -(endnum));

		return new SimpleDateFormat(format).format(calendar.getTime());
	}
	
	/**
	  * 得到本周周一
	  *
	  * @return yyyy-MM-dd
	  */
	public static String getMondayOfThisWeek(String format) {
	 	Calendar calendar = Calendar.getInstance();
	 	int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	 	if (day_of_week == 0)
	 		day_of_week = 7;
	 	calendar.add(Calendar.DATE, -day_of_week + 1);
	 	return new SimpleDateFormat(format).format(calendar.getTime());
	}
	/**
	 * 得到本周周一
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getMondayOfThisWeek() {
		Calendar calendar = Calendar.getInstance();
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		calendar.add(Calendar.DATE, -day_of_week + 1);
		return calendar.getTime();
	}
	
	 /**
	  * 得到本周周日
	  *
	  * @return yyyy-MM-dd
	  */
	 public static String getSundayOfThisWeek(String format) {
	  	Calendar calendar = Calendar.getInstance();
	  	int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	  	if (day_of_week == 0)
	  		day_of_week = 7;
	  	calendar.add(Calendar.DATE, -day_of_week + 7);
	  	return new SimpleDateFormat(format).format(calendar.getTime());
	 }

	/**
	 * 得到本周周日
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getSundayOfThisWeek() {
		Calendar calendar = Calendar.getInstance();
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		calendar.add(Calendar.DATE, -day_of_week + 7);
		return calendar.getTime();
	}
	 
	 /**
	  * 得到下周周一
	  *
	  * @return yyyy-MM-dd
	  */
	public static String getMondayOfNextWeek(String format) {
	 	Calendar calendar = Calendar.getInstance();
	 	int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	 	if (day_of_week == 0)
	 		day_of_week = 7;
	 	calendar.add(Calendar.DATE, (-day_of_week + 1)+7);
	 	return new SimpleDateFormat(format).format(calendar.getTime());
	}
	
	 /**
	  * 得到下周周日
	  *
	  * @return yyyy-MM-dd
	  */
	 public static String getSundayOfNextWeek(String format) {
	  	Calendar calendar = Calendar.getInstance();
	  	int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	  	if (day_of_week == 0)
	  		day_of_week = 7;
	  	calendar.add(Calendar.DATE, (-day_of_week + 7)+7);
	  	return new SimpleDateFormat(format).format(calendar.getTime());
	 }
	 
	/**
	 * @Title:  getSundayOfWeekNum 
	 * @Description: (西方周 习惯) 一周的开始--结束为： 周日 -- 周六
	 * @author: ziyu.zhang
	 * @date:   2016年2月22日 上午10:55:31 
	 * @update: 2016年2月22日 上午10:55:31
	 * @param format    日期格式化 - 如：yyyy-MM-dd
	 * @param WeekNum	推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
	 * @param week		周几 如 1周日，2周一。。。。6周六
	 * @return: String  
	 * @throws:
	 * Why & What is modified: <修改原因描述>
	 */
	 public static String getSundayOfWeekNum(String format,int WeekNum,int week){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, WeekNum*7);
		calendar.set(Calendar.DAY_OF_WEEK,week);
		return new SimpleDateFormat(format).format(calendar.getTime());
	 }
	

	/**
	 * 改更现在时间
	 */
	public static Date changeDate(String type, int value) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		if (type.equals("month")) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + value);
		} else if (type.equals("date")) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + value);
		}
		return calendar.getTime();
	}

	/**
	 * 更改时间
	 */
	public static Date changeDate(Date date, String type, int value) {
		if (date != null) {
			// Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			// Calendar calendar = Calendar.
			if (type.equals("month")) {
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)
						+ value);
			} else if (type.equals("date")) {
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + value);
			} else if (type.endsWith("year")) {
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + value);
			}
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * haoxw 比较时间是否在这两个时间点之间
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean checkTime(String time1, String time2,String format) {
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		Date date11 = DateUtil.str2Date(DateUtil.date2Str(date1, "yyyy-MM-dd")
				+ " " + time1,format);// 起始时间

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		Date date2 = c.getTime();
		Date date22 = DateUtil.str2Date(DateUtil.date2Str(date2, "yyyy-MM-dd")
				+ " " + time2,format);// 终止时间

		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date11);// 起始时间

		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(date22);// 终止时间

		Calendar calendarnow = Calendar.getInstance();

		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * haoxw 比较时间是否在这两个时间点之间
	 * 
	 * @param date11
	 * @param date22
	 * @return
	 */
	public static boolean checkTime(Date date11, Date date22) {

		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date11);// 起始时间

		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(date22);// 终止时间

		Calendar calendarnow = Calendar.getInstance();

		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean checkDate(String date1, String date2) {

		Date date11 = DateUtil.str2Date(date1, "yyyy-MM-dd HH:mm:ss");// 起始时间

		Date date22 = DateUtil.str2Date(date2, "yyyy-MM-dd HH:mm:ss");// 终止时间

		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date11);// 起始时间

		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(date22);// 终止时间

		Calendar calendarnow = Calendar.getInstance();

		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取interval天之前的日期
	 * 
	 * @param interval
	 * @param starttime
	 * @param pattern
	 * @return
	 */
	public static Date getIntervalDate(String interval, Date starttime,
			String pattern) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(starttime);
		temp.add(temp.DATE, Integer.parseInt(interval));
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String shijian = sdf.format(temp.getTime());
		return str2Date(shijian,pattern);
	}

	public static Date formatDate(Date date) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat bartDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = bartDateFormat1.parse(bartDateFormat.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	/**
	 * 在当前时间增加一天
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			cd.add(Calendar.DATE, n);// 增加一天

			return cd.getTime();

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 在当前时间增加一个月
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			cd.add(Calendar.MONTH, n);// 增加一个月

			return cd.getTime();

		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 在当前时间提前两小时
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static String addHour(String str,String format, int n) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(str);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			cd.add(Calendar.HOUR, n);// 增加一个月
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String shijian = sdf.format(cd.getTime());
			return shijian;

		} catch (Exception e) {
			return null;
		}
	}
	

	/**
	 * 获取前月的第一天
	 * @return
	 */
	public static String getbeforefirstDay(String format){
	     Calendar   cal_1=Calendar.getInstance();//获取当前日期
	     cal_1.add(Calendar.MONTH, -1);
	     cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
	     String firstDay = new SimpleDateFormat(format).format(cal_1.getTime());
	     return firstDay;
	}
	/**
	 * 获取前月的最后一天
	 * @return
	 */
	public static String getbeforelastDay(String format){
	     Calendar cale = Calendar.getInstance();  
	     cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
	     String lastDay = new SimpleDateFormat(format).format(cale.getTime());
	     return lastDay;
	}
    /**
     * 获取当前月第一天
     * @return
     */
	public static String getfirstDay(String format){
		 //获取当前月第一天：
	     Calendar c = Calendar.getInstance();   
	     c.add(Calendar.MONTH, 0);
	     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
	     String first = new SimpleDateFormat(format).format(c.getTime());
	     return first;
	}
	/**
	 * 获取当前天数之前指定天数
	 * @return
	 */
	public static String getbeforeDay(String format,int day){
		//获取当前月第一天：
		Calendar c = Calendar.getInstance();   
		c.add(Calendar.DATE, -day);
		String first = new SimpleDateFormat("yyyyMMdd").format(c.getTime())+"000000";
		return first;
	}
	/**
	 * 获取当前天数之前指定天数
	 * @return
	 */
	public static String getBeforeDay(String format,int day){
		//获取当前月第一天：
		Calendar c = Calendar.getInstance();   
		c.add(Calendar.DATE, -day);
		String first = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return first;
	}
	/**
	 * 获取当前月最后一天
	 * @return
	 */
	public static String getlastDay(String format){
	     Calendar ca = Calendar.getInstance();   
	     ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
	     String last = new SimpleDateFormat(format).format(ca.getTime());
	     return last;
	}
	/**
	 * 获取  MONTH 个月前的那一天 (实际意义上相隔的月天数)
	 * @return
	 */
	public static String getMonthApartFirstDay(String format,int MONTH){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - MONTH);
		String result = new SimpleDateFormat(format).format(calendar.getTime());
		return result;
	}
	/**
	 * 获取   MONTH 个月后的第一天
	 * @param format 格式化
	 * @param MONTH 1 上月 0 本月 -1 下月
	 * @return
	 */
	public static String getMonthFirstDayOfMonNum(String format,int MONTH){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String result = new SimpleDateFormat(format).format(calendar.getTime());
		return result;
	}
	/**
	 * 获取   MONTH 个月后的第一天
	 * @param MONTH 1 上月 0 本月 -1 下月
	 * @return
	 */
	public static Date getMonthFirstDayOfMonNum(int MONTH){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date result = calendar.getTime();
		return result;
	}
	
	/**
	 * 获取  MONTH 个月后的最后一天
	 * @param format 格式化
	 * @param MONTH 1 上月 0 本月 -1 下月
	 * @return
	 */
	public static String getMonthEndDayOfMonNum(String format,int MONTH){
		Calendar calendar = Calendar.getInstance();  
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-MONTH);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
		String result = new SimpleDateFormat(format).format(calendar.getTime());
		return result;
	}

	/**
	 * 获取  MONTH 个月后的最后一天
	 * @param MONTH 1 上月 0 本月 -1 下月
	 * @return
	 */
	public static Date getMonthEndDayOfMonNum(int MONTH){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-MONTH);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date result = calendar.getTime();
		return result;
	}
	
	 /**
     * 获取当前年第一天
     * @return
     */
	public static String getfirstDayofyear(String format){
		 //获取当前月第一天：
	     Calendar c = Calendar.getInstance();   
	     c.add(Calendar.MONTH, 0);
	     c.set(Calendar.DAY_OF_YEAR,1);//设置为1号,当前日期既为本月第一天
	     String first = new SimpleDateFormat(format).format(c.getTime());
	     return first;
	}
	
	/**
	 * 获取当前年最后一天
	 * @return
	 */
	public static String getlastDayofyear(String format){
	     Calendar ca = Calendar.getInstance();   
	     ca.set(Calendar.DAY_OF_YEAR, ca.getActualMaximum(Calendar.DAY_OF_YEAR)); 
	     String last = new SimpleDateFormat(format).format(ca.getTime());
	     return last;
	}
    
	/**
	 * yyyy-MM-dd HH:mm转秒
	 * @param datetime
	 * @return
	 */
	public static long timesecond(String format,String datetime){
		long st=0;
		try {
			st = new SimpleDateFormat(format).parse(datetime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return st;
	}
	/**
	 * 
	 * @Description: TODO
	 * @param @param date
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author zhibin.zhu
	 * @date 2016年4月5日
	 */
	public static String getweek(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINA);
		String week = sdf.format(date);
		return week;
	}
	
    /**
     * 获得本周的第一天，周一
     * 
     * @return
     */
    public static  Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本周的最后一天，周日
     * 
     * @return
     */
    public static  Date getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本天的开始时间，即2012-01-01 00:00:00
     * 
     * @return
     */
    public static  Date getCurrentDayStartTime() {
        Date now = new Date();
        try {
            now = shortSdf.parse(shortSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本天的结束时间，即2012-01-01 23:59:59
     * 
     * @return
     */
    public static  Date getCurrentDayEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本小时的开始时间，即2012-01-01 23:59:59
     * 
     * @return
     */
    public static  Date getCurrentHourStartTime() {
        Date now = new Date();
        try {
            now = longHourSdf.parse(longHourSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本小时的结束时间，即2012-01-01 23:59:59
     * 
     * @return
     */
    public static  Date getCurrentHourEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(longHourSdf.format(now) + ":59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本月的开始时间，即2012-01-01 00:00:00
     * 
     * @return
     */
    public static  Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前月的结束时间，即2012-01-31 23:59:59
     * 
     * @return
     */
    public static  Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的开始时间，即2012-01-01 00:00:00
     * 
     * @return
     */
    public static  Date getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的结束时间，即2012-12-31 23:59:59
     * 
     * @return
     */
    public static  Date getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     * 
     * @return
     */
    public static   Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     * 
     * @return
     */
    public static  Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 获取前/后半年的开始时间
     * @return
     */
    public static  Date getHalfYearStartTime(){
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 0);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
        
    }
    /**
     * 获取前/后半年的结束时间
     * @return
     */
    public static  Date getHalfYearEndTime(){
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /** 
     * 获取指定月的天数 
     * */  
    public static int getMonthLastDay(int year, int month)  
    {  
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }
    /** 
     * 获取指定月的星期数 
     * */  
    public static int getMonthWeeKNum(int year, int month)  
    {  
    	int maxDate = getMonthLastDay(year,month);
    	int weekNum = maxDate/7;
        if(maxDate%7>0){
        	weekNum +=1;
        }
        return weekNum;  
    }    

    /**
     * @Description: 获取第几个周几的日期
     * @param date       日期
     * @param weekNum    当月第几个
     * @param dayofweek  周几
     * @return   
     * @return Date  
     * @throws
     * @author chong.cheng
     * @date 2016年9月5日 下午4:12:22
     */
    public static Date getWeekDayTime(Date date,int weekNum,int dayofweek){
    	Calendar  calendar  = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.set(Calendar.DATE, 1);
    	int month   = calendar.get(Calendar.MONTH);
    	int dayDiff = dayofweek - calendar.get(Calendar.DAY_OF_WEEK);
    	if(dayDiff<0){
    		dayDiff +=7;
    	}
    	if(dayDiff!=0){//获取第一个周几
    	   calendar.add(Calendar.DATE, dayDiff);
    	}
    	if(weekNum==1){
    		return calendar.getTime();
    	}else{
    		calendar.add(Calendar.DATE, (weekNum-1)*7);
    		while(calendar.get(Calendar.MONTH)>month){
    			calendar.add(Calendar.DATE,-7);
    		}
    		return calendar.getTime();
    	}
    }
    
    /**
     * 
     * @Description: 获取某个时间的前几个小时时间
     * @param @return   
     * @return Date  
     * @throws
     * @author zhibin.zhu
     * @date 2016年9月12日
     */
    public static String getDateTimeBefore(Date date,int hours){
    	date.setHours(date.getHours()-hours);  
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	String format = df.format(date);
		return format;  
    }
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
	 /**
	 * 字符串的日期格式的计算   
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */ 
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff ;
			if(time1<time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day + "天" + hour + "小时" + min + "分" + sec + "秒";
	}


	public static void main(String[] args) throws ParseException {



	}
}
