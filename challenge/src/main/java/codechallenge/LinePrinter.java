package codechallenge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class LinePrinter {
  private static final String INDEX_FILE_SUFFIX = ".index";

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("Usage: java LinePrinter <file-path> <line-number>");
      System.exit(1);
    }

    String filePath = args[0];
    int lineNumber = Integer.parseInt(args[1]);

    String indexFilePath = filePath + INDEX_FILE_SUFFIX;

    List<Long> lineIndex;
    File indexFile = new File(indexFilePath);

    // If the index file doesn't exist, create it.
    if (!indexFile.exists()) {
      lineIndex = createIndex(filePath, indexFilePath);
    } else {
      lineIndex = loadIndex(indexFilePath);
    }

    if (lineNumber < 1 || lineNumber > lineIndex.size()) {
      System.err.println("Line number out of range.");
      System.exit(1);
    }

    // Get the line using the index
    printLine(filePath, lineIndex, lineNumber);
  }

  private static List<Long> createIndex(String filePath, String indexFilePath) throws IOException {
    List<Long> lineIndex = new ArrayList<>();
    try (RandomAccessFile file = getInputFile(filePath);
         BufferedWriter indexWriter = new BufferedWriter(new FileWriter(indexFilePath))) {
      long currentOffset = 0;
      while (file.readLine() != null) {
        lineIndex.add(currentOffset);
        currentOffset = file.getFilePointer();
      }
      for (Long offset : lineIndex) {
        indexWriter.write(offset.toString());
        indexWriter.newLine();
      }
    }
    return lineIndex;
  }

  private static RandomAccessFile getInputFile(String filePath) throws FileNotFoundException {
    File file = new File(filePath);
    if (!file.exists()) {
      filePath = "./" + filePath;
    }
    return new RandomAccessFile(filePath, "r");
  }

  private static List<Long> loadIndex(String indexFilePath) throws IOException {
    List<Long> lineIndex = new ArrayList<>();
    try (BufferedReader indexReader = new BufferedReader(new FileReader(indexFilePath))) {
      String line;
      while ((line = indexReader.readLine()) != null) {
        lineIndex.add(Long.parseLong(line));
      }
    }
    return lineIndex;
  }

  private static void printLine(String filePath, List<Long> lineIndex, int lineNumber) throws IOException {
    try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
      file.seek(lineIndex.get(lineNumber - 1));
      String line = file.readLine();
      System.out.println(line);
    }
  }
}
