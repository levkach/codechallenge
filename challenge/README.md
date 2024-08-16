# How to run
To run the program, execute the following command
```bash
java -jar codechallenge.jar <path-to-file> <line-number>
```

## Problem: Random line from a file
You are given a very, very large plain text file where each line contains a plain text string. The file has at most 1 billion lines; lines may have different lengths, but each line has at most 1000 characters. Your goal is to write a program that will print an arbitrary line from the file. Your program will be run many times (although you don't know exactly how many times it will be run in advance), and you don't know in advance which lines might be selected. Thus, your solution should be optimized to minimize the runtime for each additional execution. The first execution of the program may take longer than subsequent runs, and you may use additional disk storage to improve performance.
Your program should take two command-line arguments: the path of the input file from which to print lines, and the index of the line you want to print. Your
program should write the line to standard output.

## Notes
`1 000 000 000` lines times `1000` characters per line is `1 000 000 000 000` characters. 
If we assume that each character is `1 byte`, then the file is `1 TB` in size. 
This is a very large file, and we cannot load it into memory. We can use some buffer to read the file partially and process it line-by-line. 

When retrieving a particular line, we need to skip all the lines before it in order not to waste the resources.
For that we can create an index file that will store the byte offset of the beginning of each line.

### Other things to consider
- Filenames may collide with files having actually different content. We can mitigate it by calculating a hash of the file (updating it as we read from the file) and use it as a filename for the index file. 

## Solution
For reading and index creating we can use Java's `RandomAccessFile` that allows for reading from file without loading it into memory.


For retrieving a line we can use Java's `RandomAccessFile` which allows reading a file with a specific byte offset.
