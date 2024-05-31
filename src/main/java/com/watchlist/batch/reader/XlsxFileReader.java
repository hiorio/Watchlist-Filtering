package com.watchlist.batch.reader;

import com.watchlist.model.Watchlist;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxFileReader implements FileReader {

    private static final Logger logger = LoggerFactory.getLogger(XlsxFileReader.class);

    @Override
    public List<Watchlist> readFile(Resource resource) throws Exception {
        List<Watchlist> watchlist = new ArrayList<>();
        InputStream is = resource.getInputStream();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            watchlist.add(new Watchlist(
                    getCellStringValue(row.getCell(0)),
                    formatDate(getCellStringValue(row.getCell(1))),
                    getCellStringValue(row.getCell(2))
            ));
        }
        workbook.close();
        logger.info("Total items read from xlsx file {}: {}", resource.getFilename(), watchlist.size());
        return watchlist;
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    return String.valueOf((long) cell.getNumericCellValue()).trim();
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()).trim();
            case FORMULA:
                return cell.getCellFormula().trim();
            case BLANK:
                return "";
            default:
                return cell.toString().trim();
        }
    }

    private String formatDate(String date) throws ParseException {
        SimpleDateFormat[] dateFormats = new SimpleDateFormat[]{
                new SimpleDateFormat("yyyyMMdd"),
                new SimpleDateFormat("yyMMdd"),
                new SimpleDateFormat("yyyy.MM.dd"),
                new SimpleDateFormat("yy.MM.dd"),
                new SimpleDateFormat("yyyy/MM/dd"),
                new SimpleDateFormat("yy/MM/dd"),
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("yy-MM-dd")
        };

        for (SimpleDateFormat dateFormat : dateFormats) {
            try {
                Date parsedDate = dateFormat.parse(date);
                return new SimpleDateFormat("yyMMdd").format(parsedDate);
            } catch (ParseException ignored) {
            }
        }
        throw new ParseException("Unparseable date: \"" + date + "\"", 0);
    }
}
