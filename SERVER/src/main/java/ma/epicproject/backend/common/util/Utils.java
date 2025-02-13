package ma.epicproject.backend.common.util;

import ma.epicproject.backend.common.bean.*;
import ma.epicproject.backend.common.enumeration.ACTION_TYPE;
import ma.epicproject.backend.common.exception.BusinessRuleException;
import ma.epicproject.backend.common.bean.BusinessObject;
import com.itextpdf.text.BaseColor;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.util.FieldUtils;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





/**
 * Some utils functionnality
 */
public abstract class Utils {
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_WITH_HOUR = "dd/MM/yyyy HH:mm";
	public static final String DATE_FORMAT_NAME = "ddMMyyyyHHmmss";
	public final static String DATE_FORMAT_FILE = "yyMMddHHmmss";
	public static final String HOUR_FORMAT = "HH:mm:ss";
	public static final String DATE_FORMAT_ENG = "EEE MMM dd yyyy HH:mm:ss 'GMT'z";
	public static final String DATE_FORMAT_PF = "EEE MMM dd HH:mm:ss z yyyy";
	public static final String MESSASGE_RESOURCES = "messages.properties";
	public static final String APPLICATION_RESOURCES = "application.properties";
	public static final long ONE_HOUR = 60 * 60 * 1000L;
	public static final String DEFAULT_APP_NAME = "ELABS_REFONT";
	public final static Integer domaine = 1;
	
	private Utils() {

	}

	public static LocalDateTime stringEnToDate(final String strDate) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_ENG, Locale.ENGLISH);
		return LocalDateTime.parse(strDate, formatter);

	}

	public static String getCurrentDate() throws Exception {

		return dateToString(LocalDate.now());
	}

	public static String getCurrentDateTime() throws Exception {

		return dateTimeToString(LocalDateTime.now());
	}

	public static LocalDate stringToDate(final String strDate) {
		if (StringUtils.hasLength(strDate)) {
			try {
				
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
			return LocalDate.parse(strDate, formatter);
			
			} catch (Exception e) {
				throw new BusinessRuleException("errors.format.date");
			}
		}

		return null;
	}

	public static LocalDateTime stringTextToDate(final String strDate) throws Exception {
		if (StringUtils.hasLength(strDate)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PF, Locale.US);
			return LocalDateTime.parse(strDate, formatter);
		}

		return null;
	}

	public static LocalDateTime stringToDateTime(final String strDate) {
		try {
			if (StringUtils.hasLength(strDate)) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_HOUR);
				return LocalDateTime.parse(strDate, formatter);
			}
		} catch (Exception e) {
			throw new BusinessRuleException("errors.format.datetime");
		}

		return null;
	}

	public static Timestamp stringToTimestamp(final String strDate) throws Exception {
		if (StringUtils.hasLength(strDate)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
			return new Timestamp(
					LocalDateTime.parse(strDate, formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		}
		return null;
	}

	public static String dateFormatFichier() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_FILE);
		return LocalDateTime.now().format(formatter);
	}

	public static Long stringToLongTime(final String strDate) throws Exception {
		if (StringUtils.hasLength(strDate)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
			return LocalDateTime.parse(strDate, formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		}
		return null;
	}

	public static String dateToString(final LocalDate date) {
		if (date != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
			return date.format(formatter);
		}
		return "";
	}
	
	public static String dateToString(final LocalDateTime date) {
		if (date != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
			return date.format(formatter);
		}
		return "";
	}

	public static String dateTimeToString(final LocalDateTime date) {
		try {
			if (date != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_HOUR);
				return date.format(formatter);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public static String dateTimeToString(final LocalDate date) {
		try {
			if (date != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_HOUR);
				return date.format(formatter);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	public static String dateToStringWithHour(final LocalDateTime date) throws Exception {
		if (date != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_HOUR);
			return date.format(formatter);
		}
		return "";
	}

	public static String dateToStringWithHour(final Long d) throws Exception {
		if (d != null && d != 0) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_HOUR);
			LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(d), ZoneId.systemDefault());
			return date.format(formatter);
		}
		return "";
	}

	public static String timesTimpToStringWithHour(final Timestamp d) throws Exception {
		if (d != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
			LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(d.getTime()), ZoneId.systemDefault());
			return date.format(formatter);
		}
		return "";
	}

	public static String timesTimpToStringWithHour(final Long d) throws Exception {
		if (d != null) {
			LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(d), ZoneId.systemDefault());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
			return date.format(formatter);
		}
		return "";
	}

	public static String timesTimpToStringWithHourName(final Timestamp d) throws Exception {
		if (d != null) {
			LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(d.getTime()), ZoneId.systemDefault());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_NAME);
			return date.format(formatter);
		}
		return "";
	}

	public static String timesTimpToHour(final Timestamp d) throws Exception {
		if (d != null) {
			LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(d.getTime()), ZoneId.systemDefault());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(HOUR_FORMAT);
			return date.format(formatter);
		}
		return "";
	}

	public static Timestamp StringToTimesTimpWithHour(final String date) throws Exception {
		if (date != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
			return new Timestamp(
					LocalDateTime.parse(date, formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		}
		return null;
	}

	public static boolean isNoItemInArray(String[] array) {
		if (array == null)
			return true;

		if ((array.length == 1) && !StringUtils.hasText(array[0]))
			return true;

		return false;
	}

	public static boolean isNoEmpty(String[] array) {
		if (array != null && array.length > 0)
			return true;

		return false;
	}

	public static String translate(String src) {
		StringBuilder result = new StringBuilder();
		if (src != null && src.length() != 0) {
			int index = -1;
			String chars = "àâäéèêëîïôöùûüç";
			String replace = "aaaeeeeiioouuuc";
			for (int i = 0; i < src.length(); i++) {
				if ((index = chars.indexOf(src.charAt(i))) != -1)
					result.append(replace.charAt(index));
				else
					result.append(src.charAt(i));
			}
		}

		return result.toString();
	}

	public static boolean isEmail(String email) {

		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static long daysBetween(Date d1, Date d2) {
		return ((d2.getTime() - d1.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
	}

	public static URL getResourceFromcClassPath(String name) throws Exception {

		Resource resource = new ClassPathResource(name);
		return resource.getURL();
	}

	public static Calendar dateToCalendar(final Date date) throws Exception {

		if (date != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			return cal;
		}
		return null;
	}

	public static LocalDateTime addDaysToDate(LocalDateTime date, long days) throws Exception {
		if (date != null)
			return date.plusDays(days);

		return null;

	}

	public static String getProperties(String name, String key) throws Exception {

		Properties properties = null;
		InputStream inputStream = null;
		InputStreamReader reader = null;
		if (properties == null) {
			try {
				properties = new Properties();
				inputStream = new FileInputStream(getResourceFromcClassPath(name).getFile());
				reader = new InputStreamReader(inputStream, "UTF-8");
				properties.load(reader);
			} catch (Exception e) {
				throw e;
			} finally {
				if (inputStream != null)
					inputStream.close();
				if (reader != null)
					reader.close();
			}
		}

		return properties.getProperty(key);
	}

	public static String capitalize(String s_) {
		if (s_ != null && !s_.equals(""))
			return s_.substring(0, 1).toUpperCase() + s_.substring(1);
		return "";
	}

	public static String uncapitalize(String s_) {
		if (s_ != null && !s_.equals(""))
			return s_.substring(0, 1).toLowerCase() + s_.substring(1);
		return "";
	}

	public static boolean isInteger(String value) {
		try {
			if (Integer.valueOf(value) != null)
				return true;
			else
				return false;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(String value) {
		try {
			if (Long.valueOf(value) != null)
				return true;
			else
				return false;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(Object value) {
		try {
			if (Long.valueOf(String.valueOf(value)) != null)
				return true;
			else
				return false;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String value) {
		try {
			Double d = Double.parseDouble(value);
			if (d.isInfinite())
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static double formatDecimal(double d) {

		String dd = String.valueOf(d);
		try {
			BigDecimal decimalFormat = new BigDecimal(dd);
			decimalFormat = decimalFormat.setScale(2, BigDecimal.ROUND_HALF_UP);
			return decimalFormat.doubleValue();
		} catch (Exception e) {
		}

		return d;
	}

	public static BigDecimal sum(Double d1, Double d2) throws Exception {

		return new BigDecimal(d1.toString()).add(new BigDecimal(d2.toString()));
	}

	public static BigDecimal divide(Double d1, Double d2) throws Exception {
		try {
			return new BigDecimal(d1.toString()).divide(new BigDecimal(d2.toString()), 2, RoundingMode.HALF_UP);
		} catch (ArithmeticException e) {
			throw new ArithmeticException("common.error.function.divide");
		}
	}

	public static BigDecimal multiply(Double d1, Double d2) throws Exception {

		return new BigDecimal(d1.toString()).multiply(new BigDecimal(d2.toString()));
	}

	public static BigDecimal subtract(Double d1, Double d2) throws Exception {

		return new BigDecimal(d1.toString()).subtract(new BigDecimal(d2.toString()));
	}

	public static boolean isSqlDouble(int val) {
		if (val == Types.NUMERIC || val == Types.INTEGER || val == Types.DECIMAL || val == Types.DOUBLE
				|| val == Types.BIGINT || val == Types.SMALLINT)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param externalEnum
	 * @return list liste des valeurs de l'enumération
	 */
	public static <T extends Enum<T>> List<EnumBean> enumToList(final T[] externalEnum) throws Exception {

		List<EnumBean> list = new ArrayList<EnumBean>();

		for (int i = 0; i < externalEnum.length; i++) {
			String key = externalEnum.getClass().getSimpleName().replaceAll(";", "").toLowerCase() + "."
					+ externalEnum[i].toString();
			EnumBean form = new EnumBean(externalEnum[i].ordinal(), getProperties(MESSASGE_RESOURCES, key));
			list.add(form);
		}

		return list;

	}

	public static Long[] getIdTab(List<? extends BusinessObject> list) throws Exception {

		if (list != null && !list.isEmpty()) {
			Long[] ids = new Long[list.size()];
			int i = 0;
			for (BusinessObject obj : list) {
				ids[i] = obj.getId();
				i++;
			}

			return ids;
		} else
			return new Long[0];
	}
	
	
	public static List<Long> getIdsList(List<? extends BaseDto> list) throws Exception {

		List<Long> result = new ArrayList<Long>();
		if (list != null && list.size() > 0) {
			for (BaseDto obj : list) {
				if (obj.getId() == null)
					continue;
				result.add(obj.getId());
			}

		}

		return result;
	}

	public static List<Long> getIdToList(Long id) throws Exception {

		List<Long> result = new ArrayList<Long>();
		if (id != null)
			result.add(id);

		return result;

	}

	public static Long[] getIdTab(Set<? extends BusinessObject> set) throws Exception {

		if (set != null && set.size() > 0) {
			Long[] ids = new Long[set.size()];
			int i = 0;
			for (BusinessObject obj : set) {
				ids[i] = obj.getId();
				i++;
			}

			return ids;
		} else
			return new Long[0];
	}
	
	public static List<Long> getIdsListDto(List<? extends BaseDto> list) throws Exception {

		List<Long> result = new ArrayList<Long>();
		if (list != null && !list.isEmpty()) {
			for (BaseDto obj : list) {
				result.add(obj.getId());
			}

		}

		return result;
	}

	public static List<Long> getIdsListDto(Set<? extends BaseDto> list) throws Exception {

		List<Long> result = new ArrayList<Long>();
		if (list != null && !list.isEmpty()) {
			for (BaseDto obj : list) {
				result.add(obj.getId());
			}

		}

		return result;
	}

	public static List<Long> getIdsList(Set<? extends BusinessObject> list) throws Exception {

		List<Long> result = new ArrayList<Long>();
		if (list != null && !list.isEmpty()) {
			for (BusinessObject obj : list) {
				result.add(obj.getId());
			}

		}

		return result;
	}

	public static Long[] listStringToArray(List<String> list) throws Exception {

		if (list != null && !list.isEmpty()) {
			Long[] ids = new Long[list.size()];
			int i = 0;
			for (String id : list) {
				ids[i] = Long.valueOf(id);
				i++;
			}

			return ids;
		} else
			return null;
	}

	public static List<String> getIdsToList(List<? extends BusinessObject> list) throws Exception {

		if (list != null && !list.isEmpty()) {
			List<String> l = new ArrayList<String>();
			for (BusinessObject obj : list) {
				l.add(obj.getId().toString());
			}

			return l;
		} else
			return null;
	}

	public static boolean composed(String s) throws Exception {

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '.')
				return true;
		}
		return false;

	}

	public static String listToString(List<String> list) throws Exception {
		String s = "";
		if (list != null)
			for (String string : list) {
				s = s + string + ";";
			}
		return s != null && !s.isEmpty() ? s : null;
	}

	public static List<? extends Object> getSourceList(List<? extends Object> source, List<? extends Object> target)
			throws Exception {

		if (source != null && target != null) {
			for (Object src : target) {
				BusinessObject o = (BusinessObject) src;
				if (source.contains(o))
					source.remove(o);
			}
		}

		return source;

	}

	public static List<String> stringToList(String s) throws Exception {
		List<String> list = new ArrayList<String>();
		if (s != null && !s.isEmpty())
			for (int i = 0; i < s.split(";").length; i++) {
				list.add(s.split(";")[i]);
			}
		return list;
	}

	public static int getDiffYears(Date first, Date last) {
		Calendar a = getCalendar(first);
		Calendar b = getCalendar(last);
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
				|| (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
			diff--;
		}
		return diff;
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}

	public static <T extends BaseDto> List<AuditEntity> compareObjects(T newVal, T oldVal) throws Exception {
		List<AuditEntity> list = new ArrayList<AuditEntity>();
		LocalDateTime d = LocalDateTime.now();
		for (Method method : newVal.getClass().getDeclaredMethods()) {
			Annotation tagAnnotation = logAnnotation(method);
			if (tagAnnotation != null) {
				Object value1 = getMethodValue(method, newVal, tagAnnotation);
				Method method2 = null;
				if (oldVal != null)
					method2 = oldVal.getClass().getMethod(method.getName(), method.getParameterTypes());
				else if (newVal != null)
					method2 = newVal.getClass().getMethod(method.getName(), method.getParameterTypes());
				Object value2 = getMethodValue(method2, oldVal, tagAnnotation);
				if ((value2 != null && !value2.equals(value1)) || (value1 != null && !value1.equals(value2))) {
					boolean collection = false;
					if ((value1 != null && value1 instanceof Collection<?>)
							|| (value2 != null && value2 instanceof Collection<?>)) {
						collection = true;
					}
					if (collection) {
						List coll1 = new ArrayList();
						List coll2 = new ArrayList();
						if (value1 != null && value1 instanceof Collection<?>)
							coll1 = (List) value1;
						if (value2 != null && value2 instanceof Collection<?>)
							coll2 = (List) value2;
						for (Object object : added(coll2, coll1)) {
							AuditEntity auditEntity = new AuditEntity();
							auditEntity.setColonne(formatName(method.getName()));
							auditEntity.setDateTime(d);
							//auditEntity.setUserId(getCurrentUserId());
							//auditEntity.setUsername(getCurrentUserName());
							if (isLong(object)) {
								auditEntity.setNewValue(String.valueOf(object));
							} else if (object instanceof BaseDto) {
								auditEntity.setNewValue(((BaseDto) object).getId().toString());
							}
							auditEntity.setObjectId(oldVal.getId());
							auditEntity.setTypeaction(ACTION_TYPE.ADD.getDisplayText());
							list.add(auditEntity);

						}
						for (Object object : removed(coll2, coll1)) {
							AuditEntity auditEntity = new AuditEntity();
							auditEntity.setColonne(formatName(method.getName()));
							auditEntity.setDateTime(d);
							//auditEntity.setUserId(getCurrentUserId());
							//auditEntity.setUsername(getCurrentUserName());
							if (isLong(object)) {
								auditEntity.setOldValue(String.valueOf(object));
							} else if (object instanceof BaseDto) {
								auditEntity.setOldValue(((BaseDto) object).getId().toString());
							}
							auditEntity.setObjectId(oldVal.getId());
							auditEntity.setTypeaction(ACTION_TYPE.DELETE.getDisplayText());
							list.add(auditEntity);
						}

					} else {
						AuditEntity auditEntity = new AuditEntity();
						auditEntity.setColonne(formatName(method.getName()));
						auditEntity.setDateTime(d);
						//auditEntity.setUserId(getCurrentUserId());
						//auditEntity.setUsername(getCurrentUserName());
						auditEntity.setTypeaction(ACTION_TYPE.UPDATE.getDisplayText());
						auditEntity.setObjectId(oldVal.getId());
						auditEntity.setEnume(method2.getReturnType().isEnum());
						if (method2.getReturnType().isEnum()) {
							auditEntity.setNewValueFormat(((Enum) value1).name());
							auditEntity.setOldValueFormat(((Enum) value2).name());
						} else if (Boolean.class.equals(method2.getReturnType())
								|| method2.getReturnType().toString().equals("boolean")) {
							auditEntity.setNewValueFormat(Boolean.valueOf(String.valueOf(value1)) ? "OUI" : "NON");
							auditEntity.setOldValueFormat(Boolean.valueOf(String.valueOf(value2)) ? "OUI" : "NON");
						} else if (method2.getReturnType().equals(LocalDateTime.class)) {
							auditEntity.setNewValueFormat(
									value1 != null ? dateTimeToString((LocalDateTime) value1) : null);
							auditEntity.setOldValueFormat(
									value2 != null ? dateTimeToString((LocalDateTime) value2) : null);
						} else if (method2.getReturnType().getSuperclass().getSimpleName()
								.equalsIgnoreCase(BaseDto.class.getSimpleName())) {
						} else {
							auditEntity.setNewValueFormat(value1 != null ? String.valueOf(value1) : "");
							auditEntity.setOldValueFormat(value2 != null ? String.valueOf(value2) : "");
						}
						auditEntity.setNewValue(value1 != null ? String.valueOf(value1) : "");
						auditEntity.setOldValue(value2 != null ? String.valueOf(value2) : "");
						list.add(auditEntity);
					}

				}
			}
		}
		return list;

	}
	
	public static <T extends BaseDto> boolean compareObjectsDiff(T newVal, T oldVal) throws Exception {
		for (Method method : newVal.getClass().getDeclaredMethods()) {
			Annotation tagAnnotation = logAnnotation(method);
			if (tagAnnotation != null) {
				Object value1 = getMethodValue(method, newVal, tagAnnotation);
				Method method2 = null;
				if (oldVal != null)
					method2 = oldVal.getClass().getMethod(method.getName(), method.getParameterTypes());
				else if (newVal != null)
					method2 = newVal.getClass().getMethod(method.getName(), method.getParameterTypes());
				Object value2 = getMethodValue(method2, oldVal, tagAnnotation);
				boolean changed = (value2 != null && !value2.equals(value1)) || (value1 != null && !value1.equals(value2));
				if (changed)
					return true;
			}
		}
		return false;

	}

	public static Class<?> getGenericType(Method method) throws Exception {
		if (method.getGenericReturnType() instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) method.getGenericReturnType();
			Class<?> subClass = (Class<?>) pt.getActualTypeArguments()[0];
			return subClass;
		}
		return method.getReturnType();
	}

	public static Object getMethodValue(Method method, Object newVal, Annotation annotation) {
		Object object = null;
		try {
			object = method.invoke(newVal);
			if (object instanceof BaseDto) {
				object = ((BaseDto) object).getId();
			} else if (object instanceof LocalDateTime) {
				if (object != null) {
					Log log = (Log) annotation;
					if (log.format() != null && log.format().equalsIgnoreCase("dd/MM/yyyy"))
						object = dateToString(((LocalDate) object));
					else if (log.format() != null && log.format().equalsIgnoreCase("dd/MM/yyyy HH:mm"))
						object = dateTimeToString(((LocalDateTime) object));
					else
						object = ((LocalDateTime) object);
				}
			}

		} catch (Exception e) {
		}
		return object;
	}

	public static Annotation logAnnotation(Method method) throws Exception {
		for (Annotation annotation : method.getAnnotations()) {
			if (annotation.annotationType().getSimpleName().equalsIgnoreCase("Log"))
				return annotation;
		}

		return null;
	}

	public static Collection union(Collection coll1, Collection coll2) {
		Set union = new HashSet(coll1);
		union.addAll(new HashSet(coll2));
		return union;
	}

	public static List added(List coll1, List coll2) {
		List intersection = new ArrayList();
		if (coll2 != null)
			for (Object object : coll2) {
				if (coll1 != null && !coll1.contains(object))
					intersection.add(object);
			}

		return intersection;
	}

	public static List removed(List coll1, List coll2) {
		List intersection = new ArrayList();
		if (coll1 != null)
			for (Object object : coll1) {
				if (coll2 != null && !coll2.contains(object))
					intersection.add(object);
			}

		return intersection;
	}

	public static String formatName(String name) throws Exception {

		if (name != null) {
			if (name.toLowerCase().startsWith("is"))
				name = uncapitalize(name.substring(2));
			else if (name.toLowerCase().startsWith("get"))
				name = uncapitalize(name.substring(3));
		}

		return name;
	}
	
//	public static Long getCurrentUserId() {
//		if (SecurityContextHolder.getContext().getAuthentication() != null) {
//			UtilisateurDetailsImpl currentUser = (UtilisateurDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			return currentUser.getId();
//		}
//
//		return 0l;
//	}
		
//	public static String getCurrentUserName() {
//		if (SecurityContextHolder.getContext().getAuthentication() != null) {
//			UtilisateurDetailsImpl currentUser = (UtilisateurDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			return currentUser.getNomComplet();
//		}
//
//		return null;
//	}
	
	public static synchronized long getUniqueID() {
		return System.currentTimeMillis();
	}
	
		public static String formatDecimalToString(double d) {

		try {
			return new DecimalFormat("##0.00").format(d).replaceAll(",", ".");
		} catch (Exception e) {
			return "0.00";
		}
	}
	
	public static String[] decomposerDecimal(Double b) {
		String tab[] = new String[2];
		String s = formatDecimalToString(b);
		if (s.indexOf(".") != -1) {
			tab = s.split("\\.");
		} else {
			tab[0] = s;
			tab[1] = "0";
		}

		return tab;
	}

//	public static String chiffreToLettre(String s) {
//		String toLettre = "";
//		if (isInteger(s)) {
//			toLettre = FrenchNumberToWords.convert(Integer.parseInt(s));
//		}
//		return toLettre;
//	}
	
	public static String exportedList(ExportModel exportModel, String path) throws Exception {
		String fileName = "";
		if (exportModel != null && exportModel.getList() != null) {

			if (exportModel.getFileType().equalsIgnoreCase("pdf")) {

				fileName = exportPDF(exportModel, path);
			} else if (exportModel.getFileType().equalsIgnoreCase("xls")) {
				fileName = exportXLS(exportModel, path);
			}
		}

		return fileName;
	}

	private static String exportPDF(ExportModel exportModel, String path) throws Exception {

		String fileName = Utils.getUniqueID() + ".pdf";

		ReportBuilder reportBuilder = new ReportBuilder();
		reportBuilder.init();

		PdfPTable table = reportBuilder.createTable(exportModel.getTitle(), exportModel.getColumnModels());
		Font fontRow = new Font(Font.FontFamily.UNDEFINED, 11, Font.NORMAL, BaseColor.BLACK);

		for (Object object : exportModel.getList()) {
			int i = 0;
			while (i < exportModel.getColumnModels().size()) {
				String column = exportModel.getColumnModels().get(i).getName();
				Object obj = FieldUtils.getFieldValue(object, formaColumnName(column));
				if (obj == null) {
					if (methodExists(object.getClass(), "get" + capitalize(formaColumnName(column)))) {
						Method m = object.getClass().getMethod("get" + capitalize(formaColumnName(column)));
						obj = m.invoke(object);
					} else if (methodExists(object.getClass(), "is" + capitalize(formaColumnName(column)))) {
						Method m = object.getClass().getMethod("is" + capitalize(formaColumnName(column)));
						obj = m.invoke(object);
					}
				}
				table.addCell(new Phrase(formatValueObject(obj, column, exportModel.getColumnModels().get(i).getFormat()), fontRow));
				i++;
			}
		}
		reportBuilder.getDocument().add(table);
		fileName = path + fileName;
		reportBuilder.generateReport(reportBuilder.getStream(), fileName);

		return fileName;
	}

	private static String exportXLS(ExportModel exportModel, String path) throws Exception {

		String fileName = Utils.getUniqueID() + ".xls";
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();

		CellStyle style = Utils.createCellStyle(wb, "Arial", HorizontalAlignment.CENTER, (short) 13, true);

		CellStyle style3 = Utils.createCellStyle(wb, "Arial", HorizontalAlignment.CENTER, (short) 10, true);

		CellStyle columnStyle = Utils.createColumnCellStyle(wb, "Arial", HorizontalAlignment.CENTER, (short) 12, true);

		CellStyle style2 = Utils.createCellStyle(wb, "Arial", HorizontalAlignment.LEFT, (short) 12, false);

		int idx = 1;

		HSSFRow row = sheet.createRow(idx);
		HSSFCell cell = row.createCell(3);
		cell.setCellStyle(style);
		idx++;
		cell.setCellValue(exportModel.getTitle());

		row = sheet.createRow(idx);
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		idx++;
		cell.setCellValue("Exporté le : " + Utils.dateTimeToString(LocalDateTime.now()));

		int idy = 0;
		idx = 4;
		row = sheet.createRow(idx);
		for (ColumnModel columnModel : exportModel.getColumnModels()) {
			sheet.setColumnWidth(idy, 25 * 256);
			cell = row.createCell(idy);
			cell.setCellValue(columnModel.getLabel());
			cell.setCellStyle(columnStyle);
			idy++;
		}
		idx++;

		for (Object object : exportModel.getList()) {
			int i = 0;
			idy = 0;
			row = sheet.createRow(idx);
			while (i < exportModel.getColumnModels().size()) {
				String column = exportModel.getColumnModels().get(i).getName();
				Object obj = FieldUtils.getFieldValue(object, formaColumnName(column));
				if (obj == null) {
					if (methodExists(object.getClass(), "get" + capitalize(formaColumnName(column)))) {
						Method m = object.getClass().getMethod("get" + capitalize(formaColumnName(column)));
						obj = m.invoke(object);
					} else if (methodExists(object.getClass(), "is" + capitalize(formaColumnName(column)))) {
						Method m = object.getClass().getMethod("is" + capitalize(formaColumnName(column)));
						obj = m.invoke(object);
					}
				}
				cell = row.createCell(idy);
				String value = formatValueObject(obj, column, exportModel.getColumnModels().get(i).getFormat());
				cell.setCellValue(value);
				cell.setCellStyle(style2);
				i++;
				idy++;
			}
			idx++;
		}

		fileName = path + fileName;
		FileOutputStream fileOut = new FileOutputStream(fileName);
		wb.write(fileOut);
		fileOut.close();

		return fileName;

	}
	
	public static boolean methodExists(Class clazz, String methodName) throws Exception {
		boolean result = false;
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				result = true;
				break;
			}
		}
		return result;
	}

	private static String formaColumnName(String columnName) throws Exception {
		String tab[] = columnName.split("\\.");
		if (tab != null && tab.length > 0)
			return tab[0];

		return columnName;
	}

	public static String formatValueObject(Object object, String columnName, String format) throws Exception {

		if (object != null) {
			if (object instanceof Boolean) {
				boolean b = Boolean.valueOf(object.toString());
				if (b)
					return "OUI";
				else
					return "NON";
			} else if (object instanceof Enum) {
				Object obj = object.getClass().getMethod("getDisplayText").invoke(object);
				return String.valueOf(obj);
			} else if (object instanceof BaseDto) {
				Object obj = getMethodByColumn(object, columnName);
				if (obj != null && (obj instanceof LocalDate || obj instanceof LocalDateTime) && isNotNull(format)) {
					String value = "";
					if (format.equalsIgnoreCase("dd/MM/yyyy"))
						value = dateToString((LocalDate) obj);
					else if (format.equalsIgnoreCase("dd/MM/yyyy HH:mm"))
						value = dateTimeToString((LocalDateTime) obj);

					return value != null ? value : "";
				} else if (obj != null) {
					return formatValueObject(obj, columnName, format);
				}
			} else if ((object instanceof LocalDate || object instanceof LocalDateTime) && isNotNull(format)) {
				String value = "";
				if (format.equalsIgnoreCase("dd/MM/yyyy"))
					value = dateToString((LocalDate) object);
				else if (format.equalsIgnoreCase("dd/MM/yyyy HH:mm"))
					value = dateTimeToString((LocalDateTime) object);

				return value != null ? value : "";
			} else {
				return isNotNull(String.valueOf(object)) ? String.valueOf(object) : "";
			}
		}
		return "";
	}

	private static Object getMethodByColumn(Object object, String columnName) throws Exception {
		String tab[] = columnName.split("\\.");
		if (tab != null && tab.length > 3) {
			Object obj = object.getClass().getMethod("get" + capitalize(tab[1])).invoke(object);
			if (obj != null) {
				obj = obj.getClass().getMethod("get" + capitalize(tab[2])).invoke(obj);
				if (obj != null) {
					obj = obj.getClass().getMethod("get" + capitalize(tab[3])).invoke(obj);
					return obj;
				}
			}

		} else if (tab != null && tab.length > 2) {
			Object obj = object.getClass().getMethod("get" + capitalize(tab[1])).invoke(object);
			if (obj != null) {
				obj = obj.getClass().getMethod("get" + capitalize(tab[2])).invoke(obj);
				return obj;
			}

		} else if (tab != null && tab.length > 1) {
			Object obj = object.getClass().getMethod("get" + capitalize(tab[1])).invoke(object);

			if (obj != null)
				return obj;
		}

		return null;
	}

	
	public static boolean isNotNull(String s) {
		if (s != null && !s.isEmpty() && !s.equals("null"))
			return true;
		return false;
	}

	public static CellStyle createCellStyle(HSSFWorkbook wb, String fontName, HorizontalAlignment alignment, short size, boolean bold) throws Exception {
		CellStyle style = wb.createCellStyle();
		style.setAlignment(alignment);
		HSSFFont font = wb.createFont();
		font.setBold(bold);
		font.setFontHeightInPoints(size);
		font.setFontName(fontName);
		style.setFont(font);
		return style;
	}

	public static CellStyle createColumnCellStyle(HSSFWorkbook wb, String fontName, HorizontalAlignment alignment, short size, boolean bold) throws Exception {
		CellStyle style = wb.createCellStyle();
		style.setAlignment(alignment);
		HSSFFont font = wb.createFont();
		font.setBold(bold);
		font.setFontHeightInPoints(size);
		font.setColor(HSSFColor.WHITE.index);
		font.setFontName(fontName);
		style.setFont(font);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}
	
	public static int getIndexSet(Set<? extends Object> set, Object value) {
		   int result = 0;
		   for (Object entry:set) {
		     if (entry.equals(value)) return result;
		     result++;
		   }
		   return -1;
	}
	
	public static void copyIncludesProperties(Object src, Object trg, String[] args) {

		if (args != null) {
			BeanWrapper srcWrap = PropertyAccessorFactory.forBeanPropertyAccess(src);
			BeanWrapper trgWrap = PropertyAccessorFactory.forBeanPropertyAccess(trg);
			List<String> props = Arrays.asList(args);
			props.forEach(p -> trgWrap.setPropertyValue(p, srcWrap.getPropertyValue(p)));
		}
	}

	public static void copyExcludesProperties(Object src, Object dest, String[] args) {

		if (args != null)
			BeanUtils.copyProperties(src, dest, args);
	}

}