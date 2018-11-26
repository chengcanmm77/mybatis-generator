package pg.laziji.generator.mybatis;


import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
public class GeneratorService {

    @Resource
    private GeneratorMapper generatorMapper;

    @Resource
    private GeneratorUtils generatorUtils;

    public void generateZip(String[] tableNames , String zipPath) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for(String tableName : tableNames){
            List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
            generatorUtils.generatorCode(tableName, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        FileOutputStream file = new FileOutputStream(zipPath);
        file.write(outputStream.toByteArray());
        file.close();
    }
}