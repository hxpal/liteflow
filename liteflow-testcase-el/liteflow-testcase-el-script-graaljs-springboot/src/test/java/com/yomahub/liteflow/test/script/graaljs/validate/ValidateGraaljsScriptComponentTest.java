package com.yomahub.liteflow.test.script.graaljs.validate;

import com.yomahub.liteflow.script.graaljs.GraalJavaScriptExecutor;
import com.yomahub.liteflow.script.validator.ScriptValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ValidateGraaljsScriptComponentTest.class)
@EnableAutoConfiguration
public class ValidateGraaljsScriptComponentTest {
    @Test
    public void testGraaljsScriptComponentValidateFunction(){
        String correctScript = "                var a=3;\n" +
                "                var b=2;\n" +
                "                var c=1;\n" +
                "                var d=5;\n" +
                "\n" +
                "                function addByArray(values) {\n" +
                "                    var sum = 0;\n" +
                "                    for (var i = 0; i < values.length; i++) {\n" +
                "                        sum += values[i];\n" +
                "                    }\n" +
                "                    return sum;\n" +
                "                }\n" +
                "\n" +
                "                var result = addByArray([a,b,c,d]);\n" +
                "\n" +
                "                defaultContext.setData(\"s1\",parseInt(result));";
        // 语法错误
        String wrongScript = "                var a=3;\n" +
                "                var b=2;\n" +
                "                var c=1;\n" +
                "                var d=5;\n" +
                "\n" +
                "                fn addByArray(values) {\n" +
                "                    var sum = 0;\n" +
                "                    for (var i = 0; i < values.length; i++) {\n" +
                "                        sum += values[i];\n" +
                "                    }\n" +
                "                    return sum;\n" +
                "                }\n" +
                "\n" +
                "                var result = addByArray([a,b,c,d]);\n" +
                "\n" +
                "                defaultContext.setData(\"s1\",parseInt(result));";
        Assertions.assertTrue(ScriptValidator.validate(correctScript));
        Assertions.assertFalse(ScriptValidator.validate(wrongScript));
    }
}
