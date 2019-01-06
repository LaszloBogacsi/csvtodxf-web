package com.laszlobogacsi.csvtodxfwebservice.convertjob;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class FileCompressionInfo {
        final String fileToCompressPath;
        final String compressedFilePath;
}
