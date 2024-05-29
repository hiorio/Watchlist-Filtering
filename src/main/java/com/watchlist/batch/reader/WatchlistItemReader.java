package com.watchlist.batch.reader;

import com.watchlist.model.Watchlist;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class WatchlistItemReader implements ItemReader<Watchlist> {

    private final List<Watchlist> watchlists = new ArrayList<>();
    private Iterator<Watchlist> watchlistIterator;

    public WatchlistItemReader() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            // 파일 다 읽는거 아니고 들어온거 하나만 읽는건데
            // 파일 형태가 바뀔 수 있으니까 fileReader 같은 인터페이스와 이걸 구현한 xlsx, txt 구현체를 각각 만들어두고
            // 확장자 바뀔 때마다 구현체만 갈아끼우면 확장성이 있을듯 !!!???
            Resource[] resources = resolver.getResources("classpath:watchlist/*");
            for (Resource resource : resources) {
                if (resource.getFilename().endsWith(".txt")) {
                    watchlists.addAll(readFromTxtFile(resource));
                } else if (resource.getFilename().endsWith(".xlsx")) {
                    watchlists.addAll(readFromXlsxFile(resource));
                }
            }
            this.watchlistIterator = watchlists.iterator();
//            System.out.println("WatchlistItemReader initialized with " + watchlists.size() + " items.");
            log.info("WatchlistItemReader initialized with {} items.", watchlists.size()); // 로그
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WatchlistItemReader: " + e.getMessage(), e);
        }
    }

    @Override
    public Watchlist read() {
        if (watchlistIterator != null && watchlistIterator.hasNext()) {
            Watchlist item = watchlistIterator.next();
            System.out.println("Read item: " + item);
            return item;
        }
        return null;
    }

    private List<Watchlist> readFromTxtFile(Resource resource) throws Exception {
        List<Watchlist> watchlist = new ArrayList<>();
        InputStream is = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("\\|\\|\\|");
            if (tokens.length == 3) {
                // 요것도 setter 쓰지 말고 생성자로 바로 필드 주입
                Watchlist item = new Watchlist();
                item.setCustName(tokens[0].trim());
                item.setBirthday(formatDate(tokens[1].trim()));
                item.setNation(tokens[2].trim());
                watchlist.add(item);
            } else {
                System.out.println("Skipping line due to incorrect format: " + line);
            }
        }
        return watchlist;
    }

    private List<Watchlist> readFromXlsxFile(Resource resource) throws Exception {
        List<Watchlist> watchlist = new ArrayList<>();
        InputStream is = resource.getInputStream();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
//            if (row.getRowNum() == 0) {
//                continue; // skip header row
//            }

            // 생성자 주입
            Watchlist item = new Watchlist();
            item.setCustName(getCellStringValue(row.getCell(0)));
            item.setBirthday(formatDate(getCellStringValue(row.getCell(1))));
            item.setNation(getCellStringValue(row.getCell(2)));
            watchlist.add(item);
        }
        workbook.close();
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
