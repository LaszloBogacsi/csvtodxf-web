package com.laszlobogacsi.csvtodxfwebservice.file;

import com.laszlobogacsi.csvtodxfwebservice.FileTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class GZipFileCompressorServiceTest extends FileTest {
    private static final String DESTINATION = "src/test/resources";
    private final String pathToSave = DESTINATION + File.separator;
    private final String pathToRead = DESTINATION + File.separator;
    private final String gzipTestPath = pathToSave + "fileToZip.gz";

    private FileCompressorService gzipService;

    @Before
    public void setUp() throws Exception {
        gzipService = new GZipFileCompressorService();
    }

    @After
    public void tearDown() throws Exception {
        deleteDirectory(new File(gzipTestPath));
    }

    @Test
    public void shouldCompressAFile() {
        gzipService.compress(pathToRead + "fileToZip.csv", gzipTestPath);
        assertTrue(isFileExists(gzipTestPath));
    }
}