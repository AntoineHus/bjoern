package server.commands.importcsv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orientechnologies.common.log.OLogManager;
import com.orientechnologies.orient.server.config.OServerCommandConfiguration;
import com.orientechnologies.orient.server.network.protocol.http.OHttpRequest;
import com.orientechnologies.orient.server.network.protocol.http.OHttpResponse;
import com.orientechnologies.orient.server.network.protocol.http.OHttpUtils;
import com.orientechnologies.orient.server.network.protocol.http.command.OServerCommandAbstract;

import server.components.orientdbImporter.ImportCSVRunnable;
import server.components.orientdbImporter.ImportJob;

public class ImportCSVHandler extends OServerCommandAbstract
{

	private static final Logger logger = LoggerFactory
			.getLogger(ImportCSVHandler.class);

	private Thread importThread;

	public ImportCSVHandler(final OServerCommandConfiguration iConfiguration)
	{
	}

	@Override
	public boolean execute(OHttpRequest iRequest, OHttpResponse iResponse)
			throws Exception
	{
		logger.info("Importer called");

		ImportJob importJob = getImportJobFromRequest(iRequest);

		startImporterThread(importJob);
		OLogManager.instance().warn(this, "Import Thread started");

		iResponse.send(OHttpUtils.STATUS_OK_CODE, "OK", null,
				OHttpUtils.CONTENT_TEXT_PLAIN, "");

		OLogManager.instance().warn(this, "Response sent.");

		return false;
	}

	private ImportJob getImportJobFromRequest(OHttpRequest iRequest)
	{
		String[] urlParts = checkSyntax(
				iRequest.url,
				4,
				"Syntax error: importcsv/<nodeFilename>/<edgeFilename>/<dbName>/");

		return new ImportJob(urlParts[1], urlParts[2], urlParts[3]);
	}

	private void startImporterThread(ImportJob graphFiles)
	{
		importThread = new Thread(new ImportCSVRunnable(graphFiles));
		importThread.start();
	}

	@Override
	public String[] getNames()
	{
		return new String[] { "GET|importcsv/*" };
	}

}
