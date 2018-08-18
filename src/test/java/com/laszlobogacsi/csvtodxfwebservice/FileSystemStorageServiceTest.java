package com.laszlobogacsi.csvtodxfwebservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class FileSystemStorageServiceTest {

    StorageService fileStorageService;
    MockMultipartFile mockFile;
    public static final String DESTINATION = "src/test/resources/test-upload-dir";
    public static final String uniqueDirectoryName = "uniqueTestName";
    public final String pathToSave = DESTINATION + File.separator + uniqueDirectoryName;

    @Autowired
    UploadProperties configuration;

    @Before
    public void setUp() throws Exception {
        fileStorageService = new FileSystemStorageService(configuration);
        mockFile = new MockMultipartFile("testFile", "testFile.csv", "text/plain", "1,2,3,4,5,6".getBytes(StandardCharsets.UTF_8));

    }

    @After
    public void tearDown() throws Exception {
        deleteDirectory(new File(DESTINATION));
    }

    @Test
    public void shouldCreateUniqueDirectory() {
        fileStorageService.store(mockFile, pathToSave);
        String[] directories = new File(DESTINATION).list();
        assertNotNull(directories);
        assertThat(directories.length, greaterThan(0));
        assertEquals(uniqueDirectoryName, directories[0]);
    }

    @Test
    public void shouldSaveFileToDestinationDirectory() {
        fileStorageService.store(mockFile, pathToSave);
        File[] files = new File(pathToSave).listFiles();
        assertNotNull(files);
        assertTrue(files[0].exists());
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}