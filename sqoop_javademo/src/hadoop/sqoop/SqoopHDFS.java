package hadoop.sqoop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sqoop.tool.ImportTool;

import com.cloudera.sqoop.SqoopOptions;
import com.cloudera.sqoop.tool.ExportTool;

//Example of an Export/Import job for HDFS and sqoop written in Java.
public class SqoopHDFS 
{
	Log log = LogFactory.getLog(SqoopHDFS.class);

	// Constant strings representing sqoop options
	private static final String JOB_NAME = "Sqoop HDFS Job";
	private static final String MAPREDUCE_JOB = "HDFS Map Reduce Job";
	private static final String DBURL = "jdbc:mysql://localhost:8088/hdfs";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "pass";
	private static final String HADOOP_HOME = "/home/jeff/hadoop/hadoop-1.2.1";
	private static final String HDFS_DIR = "/usr/jeff";
	private static final String JAR_OUTPUT_DIR = "/home/jeff/tmp/";

	public static void main(String[] args) {
		SqoopHDFS js = new SqoopHDFS();
		js.exportFromHadoop("hadoop_export");
	}

	/**
	 * Exports data from the HDFS file system to MySQL
	 * @param RDBMS Table Name
	 */
	public void exportFromHadoop(String table){
		log.info("Exporting data");
		String outCols[] = {"id","name","description"};
		SqoopOptions options = new SqoopOptions(DBURL,table);
		options.setDriverClassName(DRIVER);
		options.setUsername(USERNAME);
		options.setPassword(PASSWORD);
		options.setFieldsTerminatedBy(',');
		options.setHadoopMapRedHome(HADOOP_HOME);
		options.setJobName(JOB_NAME);
		options.setLinesTerminatedBy('\n');
		options.setMapreduceJobName(MAPREDUCE_JOB);
		options.setTableName(table);
		options.setJarOutputDir(JAR_OUTPUT_DIR);
		options.setClearStagingTable(true);
		options.setExportDir(HDFS_DIR + table);
		options.setDbOutputColumns(outCols);
		options.setFieldsTerminatedBy(',');
		options.setUpdateMode(SqoopOptions.UpdateMode.AllowInsert);

		ExportTool it = new ExportTool();
		int retVal = it.run(options);
		if(retVal == 0){
			log.info("Success");
		}
		else{
			log.info("Failure");
		}
	}

	/**
	 * Imports data from MySQL (RDBMS) to HDFS
	 * @param RDBMS Table Name
	 */
	public void importToHadoop(String table){

		SqoopOptions options = new SqoopOptions(DBURL,table);
		options.setDriverClassName(DRIVER);
		options.setUsername(USERNAME);
		options.setPassword(PASSWORD);
		options.setFieldsTerminatedBy('\t');
		options.setHadoopMapRedHome(HADOOP_HOME);
		options.setJobName(JOB_NAME);
		options.setLinesTerminatedBy('\n');
		options.setMapreduceJobName(MAPREDUCE_JOB);
		options.setTableName(table);
		options.setJarOutputDir(JAR_OUTPUT_DIR);

		ImportTool it = new ImportTool();
		int retVal = it.run(options);
		if(retVal == 0) {
			log.info("Success");
		}
		else{
			log.info("Failure");
		}
	}
}
