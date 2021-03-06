package io.github.pleuvoir.service.metadata;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.pleuvoir.bean.ColumnExtend;
import io.github.pleuvoir.service.convert.ConverterService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultResultSetMetaDataService implements ResultSetMetaDataService {

	@Autowired
	private DataSource dataSource;
	
	@Override
	@SneakyThrows
	public List<ColumnExtend> query(String sql, ConverterService convertTypeService) {
		log.info("[*] 开始执行sql：{}", sql);
		PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
		ResultSetMetaData metaData = ps.getMetaData();
		int columnCount = metaData.getColumnCount();
		log.info("该SQL每行记录共有{}列", columnCount);
		List<ColumnExtend> ColumnExtendList = new LinkedList<ColumnExtend>();
		for (int i = 1; i <= columnCount; i++) {
			ColumnExtend columnExtend = new ColumnExtend();
			columnExtend.setColumnName(metaData.getColumnName(i));
			columnExtend.setColumnClassName(metaData.getColumnClassName(i));
			columnExtend.setColumnTypeName(metaData.getColumnTypeName(i));
			columnExtend.setIsNullable(metaData.isNullable(i) == 1 ? "true" : "false");
			columnExtend.setPrecision(metaData.getPrecision(i));
			columnExtend.setScale(metaData.getPrecision(i));
			columnExtend.setColumnDisplaySize(metaData.getColumnDisplaySize(i));
			columnExtend.setConvertedType(convertTypeService.convert(metaData.getColumnTypeName(i))); // 转换后的类型
			columnExtend.setField(toCamelCase(metaData.getColumnName(i))); // 字段名
			ColumnExtendList.add(columnExtend);
		}
		return ColumnExtendList;
	}

	
	private String toCamelCase(String s) {
		final char SEPARATOR = '_';
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
