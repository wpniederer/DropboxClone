# StorageCube
A Dropbox clone for learning JUnit

## How to use
1. Import the project using Maven
2. Edit syncDir var in StorageDrive
3. Edit bucket var in S3Operations
3. Add files to to that directory

## Limitations
1. In order to delete a directory you must delete all the items in the directory
2. You cannot add a directory with files; the files will be ignored. First, create a directory. Then add the desired files
3. If nested directories are quickly added, then a file is added to the inner most directory, sometimes FileWatcher will miss that file and will not be able to recognize that file.

## About Unit tests
1. Make sure to do the IntegrationTest before adding anything to the s3 bucket.
2. S3OperationsTest not working

### Note
This is a prototype:
Keep an eye out for more ease of use features once I have time to focus on this project more, check out the issues tab for planned features!
