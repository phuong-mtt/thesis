# Documents
  1. Folder of documents: https://drive.google.com/drive/folders/1hstoqWlTvAHZTqccA8HLGpBb9sYI8V0l?usp=sharing
  2. Authentication Users API Design: https://docs.google.com/document/d/1mijDWDyYk8PpGn3DbDMEE80K5a_nGTlj/edit?usp=sharing&ouid=101716044135938983401&rtpof=true&sd=true
  3. Test cases for Authentication Service: https://docs.google.com/spreadsheets/d/193gTcSL_CZCPHl7-yBPrWOi5ELWJIgzb/edit?usp=share_link&ouid=101716044135938983401&rtpof=true&sd=true
  
# Run project
  1. Install IntelliJ, Maven 3.9.1, JDK 20
  2. Clone this git repository
  
# TDD
### How to run testcase?
  1. Go to src/test/java/APITesting/{{ts_name}
  2. Click Run button before each method Test
  3. IDE automatically run test case
### How to see test report?
  1. After running successfully, Allure reports will be generated.
  2. To see report, go to ExtentReports/ExtendReport.html/Open in/Browser/Chrome
  
  
# BDD
### How to run each test scenario?
  1. Go to feature file containing test scenario need to run.
  2. Right-click on this file and click Run
  3. IDE automatically run scenario

### How to run all test scenarios?
  1. Find testRunner.java file
  2. Set up feature file that need to run by copy its path into CucumberOption/features
  3. Right-click on this feature file and click Run
  4. IDE automatically run scenario
  
### How to see test reports?
  1. After running successfully, Cucumber support users to see test reports by clicking links at the bottom of test result
  2. Click this link, users are redirected to Cucumber Reports and users can see test reports in here



