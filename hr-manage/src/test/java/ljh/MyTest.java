package ljh;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerBorderImpl;
import com.handge.hr.common.utils.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.*;

/**
 * @author Liujuhao
 * @date 2018/7/27.
 */
public class MyTest {

    @Test
    public void export() throws Exception {
        String inputPath = "src/main/test/ljh/work.txt";
        TemplateExportParams params = new TemplateExportParams("src/main/test/ljh/周报模板-大数据.xlsx");
        params.setStyle(ExcelExportStylerBorderImpl.class);
        params.setHeadingStartRow(1);
        params.setHeadingRows(1);
        Map<String, Object> map = new HashMap<String, Object>();
        List<WeekReportEntity> results = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(inputPath));
        String line;
        Calendar calendar = Calendar.getInstance();
        String end = DateUtil.date2Str(calendar.getTime(), "yyyy年MM月dd日");
        calendar.add(Calendar.DAY_OF_MONTH, -4);
        String start = DateUtil.date2Str(calendar.getTime(), "yyyy年MM月dd日");
        map.put("start", start);
        map.put("end", end);
        while ((line = br.readLine()) != null) {
            line = line.replace(" ", "");
            System.out.println(line);
            String[] strings = line.split("\\|");
            System.out.println("1:" + strings[1]);//project
            System.out.println("2:" + strings[2]);//author
            System.out.println("5:" + strings[5]);//content
            System.out.println("6:" + strings[6]);//priority
            System.out.println("7:" + strings[7]);//isDocument
            System.out.println("8:" + strings[8]);//rate
            WeekReportEntity wre = new WeekReportEntity();
            wre.setName(strings[2]);
            String content = "【" + strings[1] + "】" + strings[5];
            wre.setPlanContent(content);
            wre.setActualContent(content);
            wre.setRate("-".equals(strings[8]) ? "长期持续" : strings[8]);
            results.add(wre);
        }
        String currentDate = DateUtil.date2Str(new Date(), "yyyyMMdd");
        String fileName = "周报-大数据-" + currentDate;
        Workbook workbook = ExcelExportUtil.exportExcel(params, WeekReportEntity.class, results, map);
        FileOutputStream fos = new FileOutputStream("src/main/test/ljh/" + fileName + ".xlsx");
        workbook.write(fos);
        fos.close();
        br.close();
    }

    @Test
    public void genSeries() throws Exception {
        List peoples = new ArrayList() {
            {
                add("刘基豪");
                add("郭大露");
                add("向超");
                add("龚晓宇");
                add("马建福");
                add("刘倩");
            }
        };
        int size = peoples.size();
        Random random = new Random();
        List result = new ArrayList();
        while (result.size() < size) {
            int index = random.nextInt(peoples.size());
            result.add(peoples.get(index));
            peoples.remove(index);
        }
        System.out.println(result);
    }
}
