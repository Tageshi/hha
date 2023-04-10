package org.example;

import static org.junit.Assert.assertTrue;

import com.bin.user.api.OutcomeService;
import com.bin.user.impl.OutcomeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    private OutcomeServiceImpl outcomeService = new OutcomeServiceImpl();
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testAddOutcomeRecordByAlipayExcel() throws IOException {
        // 读取本地Excel文件
        File file = new File("C:\\Users\\23215\\Desktop\\alipay_record_20230111_174457.xlsx");
        FileInputStream input = new FileInputStream(file);

        // 创建一个MultipartFile对象
        MockMultipartFile mockFile = new MockMultipartFile("file", input);

        // 调用服务类的方法
        boolean result = outcomeService.addOutcomeRecordByAlipayExcel(mockFile, 1L);

        // 断言结果是否为true
        assertTrue(result);
    }

}
