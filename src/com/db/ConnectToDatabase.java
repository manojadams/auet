package com.db;

public class ConnectToDatabase {

}


package com.softwareag.upgrade.utility.ui;

import java.awt.CardLayout;
import com.softwareag.upgrade.utility.ui.support.jtable.*;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.Vector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.LayoutStyle;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.softwareag.upgrade.utility.Common.ResetInputVariables;
import com.softwareag.upgrade.utility.core.AutoUpgradeUsingUI;
import com.softwareag.upgrade.utility.Main;
import com.softwareag.upgrade.utility.PropertiesFiles;
import com.softwareag.upgrade.utility.core.Inputs;
import com.softwareag.upgrade.utility.migration.OneData.OneDataExtractDbDetailFromFile;
import com.softwareag.upgrade.utility.migration.commonfunctions.CommonFunctions;
import com.softwareag.upgrade.utility.migration.commonfunctions.DatabaseMigration;
import com.softwareag.upgrade.utility.resources.I18n;
import com.softwareag.upgrade.utility.resources.Usability;
import com.softwareag.upgrade.utility.ui.support.DialougeBoxes;
import com.softwareag.upgrade.utility.ui.support.UpdateJDBCDBParameters;
import com.softwareag.upgrade.utility.ui.support.jtable.ButtonColumn;
import com.softwareag.upgrade.utility.ui.support.jtable.CheckBoxCell;
import com.softwareag.upgrade.utility.ui.support.jtable.CheckBoxHeader;
import com.softwareag.upgrade.utility.ui.support.jtable.ColumnGroup;
import com.softwareag.upgrade.utility.ui.support.jtable.CustomHeaderRenderer;
import com.softwareag.upgrade.utility.ui.support.jtable.GroupableTableHeader;
import com.softwareag.upgrade.utility.ui.support.jtable.JTableDataBase;
import com.softwareag.upgrade.utility.ui.support.jtable.PasswordCellRenderer;
import com.softwareag.upgrade.utility.ui.support.ParseCSVFile;


import loggerClasses.LoggerClassForFullLog;
import loggerClasses.loggerClassForSummary;
import loggerClasses.loggerClassForconsole;

import java.lang.reflect.Method;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * JPanel for Database details screen. 
 * It will appear depending on the product selection and upgrade type details. 
 * @author chm
 */
public class JPanelDatabaseDetailsScreen extends javax.swing.JPanel {
	
	@Override //
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	private static final String HTML_DBURL_TOOLTIP = "<html>For SQL Server: jdbc:wm:sqlserver://[server]:[port];databaseName=[value] <br>For Oracle: jdbc:wm:oracle://[server]:[port];serviceName=[value]</html>";
	private static final String J_RADIO_BUTTON_DATABASE_UPGRADE_CLONE = I18n.get("JPanelDatabaseDetailsScreen.jRadioButtonDataBaseUpgradeClone");
	
	private static final String J_CHECKBOX_DATABASE_UPGRADE_DATABASE_ACTION = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.DatabaseAction");
	
	private static final String J_RADIO_BUTTON_DATABASE_UPGRADE_EXISTING = I18n.get("JPanelDatabaseDetailsScreen.jRadioButtonDataBaseUpgradeExisting");
	
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_ERROR_MESSAGE = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.errorMessage");
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_PASS_MESSAGE = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.passMessage");
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_INVALID_URL = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.InvalidURL");
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_EMPTY_USERNAME = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.EmptyUsername");
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_EMPTY_PASSWORD = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.EmptyPassword");
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_EMPTY_URL = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.EmptyURL");
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_EMPTY_URL_USERNAME = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.EmptyURLUsername");
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_EMPTY_USERNAME_PASSWORD = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.EmptyUsernamePassword");
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_EMPTY_URL_PASSWORD = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.EmptyURLPassword");
	private static final String J_TABLE_OPTIMIZE_DB_DETAILS_EMPTY_URL_USERNAME_PASSWORD = I18n.get("JPanelDatabaseDetailsScreen.jTableOptimizeDBDetails.EmptyURLUsernamePassword");
	
	private static final String INCORRECT_TABLESPACE_DETAILS = I18n.get("JPanelDatabaseDetailsScreen.IncorrectTablespaceDetails"); //$NON-NLS-1$
	private static final String INCORRECT_DATABASE_DETAILS = I18n.get("JPanelDatabaseDetailsScreen.IncorrectDatabaseDetails"); //$NON-NLS-1$

	private static final String POP_UP_TITLE_CONNECTION_STATUS = I18n.get("JPanelDatabaseDetailsScreen.PopUpTitle.ConnectionStatus"); //$NON-NLS-1$
	private static final String POP_UP_TITLE_INCORRECT_TABLESPACE_DETAILS = I18n.get("JPanelDatabaseDetailsScreen.popUpTitle.IncorrectTablespaceDetails"); //$NON-NLS-1$
	private static final String POP_UP_TITLE_INCORRECT_DATABASE_DETAILS = I18n.get("JPanelDatabaseDetailsScreen.popUpTitle.IncorrectDatabaseDetails"); //$NON-NLS-1$
	private static final String POP_UP_TITLE_WARNING = I18n.get("JPanelDatabaseDetailsScreen.popUpTitle.Warning"); //$NON-NLS-1$
	
	private static final String WARNING_POPUP_MESSAGE_5 = I18n.get("JPanelDatabaseDetailsScreen.warningPopupMessage.5"); //$NON-NLS-1$
	private static final String WARNING_POPUP_MESSAGE_4 = I18n.get("JPanelDatabaseDetailsScreen.warningPopupMessage.4"); //$NON-NLS-1$
	private static final String WARNING_POPUP_MESSAGE_3 = I18n.get("JPanelDatabaseDetailsScreen.warningPopupMessage.3"); //$NON-NLS-1$
	private static final String WARNING_POPUP_MESSAGE_2 = I18n.get("JPanelDatabaseDetailsScreen.warningPopupMessage.2"); //$NON-NLS-1$
	private static final String WARNING_POPUP_MESSAGE_1 = I18n.get("JPanelDatabaseDetailsScreen.warningPopupMessage.1"); //$NON-NLS-1$
	
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String FAIL = "FAIL"; //$NON-NLS-1$
	private static final String PASS = "PASS"; //$NON-NLS-1$
	private static final String COMMA_FOLLOWEDBY_SPACE = ", "; //$NON-NLS-1$
	private static final String NEXTLINE = "\n"; //$NON-NLS-1$
	private static final String FULLSTOP_FOLLOWEDBY_SPACE = ". "; //$NON-NLS-1$
	private static final String UNKNOWN_IMAGE_ICON = I18n.get("JPanelDatabaseDetailsScreen.unknownImageIcon"); //$NON-NLS-1$
	
	private static final String J_LABLE_OF_DESCRIPTION_TEXT_OF_HEADLINE = I18n.get("JPanelDatabaseDetailsScreen.jLableOfDescriptionTextOfHeadline"); //$NON-NLS-1$
	private static final String J_LABEL_OF_HEADLINE = I18n.get("JPanelDatabaseDetailsScreen.jLabelOfHeadline"); //$NON-NLS-1$
	private static final String J_LABEL_OF_DATABASE_UPGRADE_TYPE = I18n.get("JPanelDatabaseDetailsScreen.jLabelOfDatabaseUpgradeType");
	private static final String J_LABEL_OF_DATABASE_RDBMS = I18n.get("JPanelDatabaseDetailsScreen.jLabelOfRDBMS");
	private static final String J_BUTTON_NEXT = I18n.get("JPanelDatabaseDetailsScreen.jButtonNext");
	private static final String J_BUTTON_BACK = I18n.get("JPanelDatabaseDetailsScreen.jButtonBack"); //$NON-NLS-1$
	private static final String J_BUTTON_CANCEL = I18n.get("JPanelDatabaseDetailsScreen.jButtonCancel"); //$NON-NLS-1$
	private static final String COLUMN_GROUPS = I18n.get("JPanelDatabaseDetailsScreen.columnGroups"); //$NON-NLS-1$
	private static final String COLUMN_GROUPSCreateAndMigrate = I18n.get("JPanelDatabaseDetailsScreen.columnGroupsMigrateAndCreate");
	private static final String J_PANEL_DATABASE_COMPONENTS = I18n.get("JPanelDatabaseDetailsScreen.jPanelDatabaseComponents"); //$NON-NLS-1$
	private static final String J_PANEL_OPTIMZE_DB_DETAILS = I18n.get("JPanelDatabaseDetailsScreen.jPanelOptimzeDBDetails"); //$NON-NLS-1$
	private static final String J_PANEL_DATABASE_ORIGINAL_OR_CLONED = I18n.get("JPanelDatabaseDetailsScreen.jPanelDatabaseOriginalOrCloned"); 
	
	private static final String J_BUTTON_TEST_CONNECTION = I18n.get("JPanelDatabaseDetailsScreen.jButtonTestConnection"); //$NON-NLS-1$
	private static final String J_TEXT_AREA_ABOUT_TABLE_SPACE_DESCRIPTION = I18n.get("JPanelDatabaseDetailsScreen.jTextAreaAboutTableSpaceDescription"); //$NON-NLS-1$
	
	private static final String POOL_NAMES_STAGING = I18n.get("JPanelDatabaseDetailsScreen.poolNames.Staging"); //$NON-NLS-1$
	private static final String POOL_NAMES_REPORTING = I18n.get("JPanelDatabaseDetailsScreen.poolNames.Reporting"); //$NON-NLS-1$
	private static final String POOL_NAMES_XREF = I18n.get("JPanelDatabaseDetailsScreen.poolNames.Xref"); //$NON-NLS-1$
	private static final String POOL_NAMES_TN = I18n.get("JPanelDatabaseDetailsScreen.poolNames.TN"); //$NON-NLS-1$
	private static final String POOL_NAMES_PROCESS_ENGINE = I18n.get("JPanelDatabaseDetailsScreen.poolNames.ProcessEngine"); //$NON-NLS-1$
	private static final String POOL_NAMES_PROCESS_AUDIT = I18n.get("JPanelDatabaseDetailsScreen.poolNames.ProcessAudit"); //$NON-NLS-1$
	private static final String POOL_NAMES_IS_INTERNAL = I18n.get("JPanelDatabaseDetailsScreen.poolNames.ISInternal"); //$NON-NLS-1$
	private static final String POOL_NAMES_IS_CORE_AUDIT = I18n.get("JPanelDatabaseDetailsScreen.poolNames.ISCoreAudit"); //$NON-NLS-1$
	private static final String POOL_NAMES_DOCUMENT_HISTORY = I18n.get("JPanelDatabaseDetailsScreen.poolNames.DocumentHistory"); //$NON-NLS-1$
	private static final String POOL_NAMES_ARCHIVING = I18n.get("JPanelDatabaseDetailsScreen.poolNames.Archiving"); //$NON-NLS-1$
	private static final String POOL_NAMES_MWS = I18n.get("JPanelDatabaseDetailsScreen.poolNames.Mws"); //$NON-NLS-1$
	private static final String POOL_NAMES_TRADINGNETWORKS = I18n.get("JPanelDatabaseDetailsScreen.poolNames.TradingNetworks"); //$NON-NLS-1$
	private static final String POOL_NAMES_TRADING_NETWORKS_ARCHIVE = I18n.get("JPanelDatabaseDetailsScreen.poolNames.TradingNetworksArchive"); //$NON-NLS-1$
	private static final String POOL_NAMES_ANALYSIS = I18n.get("JPanelDatabaseDetailsScreen.poolNames.Analysis"); //$NON-NLS-1$
	private static final String POOL_NAMES_PROCESS_TRACKER = I18n.get("JPanelDatabaseDetailsScreen.poolNames.ProcessTracker"); //$NON-NLS-1$
	private static final String POOL_NAMES_DATA_PURGE = I18n.get("JPanelDatabaseDetailsScreen.poolNames.DataPurge"); //$NON-NLS-1$
	private static final String POOL_NAMES_DISTRIBUTED_LOCKING = I18n.get("JPanelDatabaseDetailsScreen.poolNames.DistributedLocking"); //$NON-NLS-1$
	private static final String POOL_NAMES_ONEDATAMETADATA = I18n.get("JPanelDatabaseDetailsScreen.poolNames.OneDataMetadata");
	
	private static final String TABLE_OPTIMIZE_DB_DETAILS_COLUMN_HEADINGS_CONNECTION_STATUS = I18n.get("JPanelDatabaseDetailsScreen.tableOptimizeDBDetailsColumnHeadings.ConnectionStatus"); //$NON-NLS-1$
	private static final String TABLE_OPTIMIZE_DB_DETAILS_COLUMN_HEADINGS_FOR_INDEX = I18n.get("JPanelDatabaseDetailsScreen.tableOptimizeDBDetailsColumnHeadings.ForIndex"); //$NON-NLS-1$
	private static final String TABLE_OPTIMIZE_DB_DETAILS_COLUMN_HEADINGS_FOR_DATA = I18n.get("JPanelDatabaseDetailsScreen.tableOptimizeDBDetailsColumnHeadings.ForData"); //$NON-NLS-1$
	private static final String TABLE_OPTIMIZE_DB_DETAILS_COLUMN_HEADINGS_USE_DEFAULT = I18n.get("JPanelDatabaseDetailsScreen.tableOptimizeDBDetailsColumnHeadings.UseDefault"); //$NON-NLS-1$
	private static final String TABLE_OPTIMIZE_DB_DETAILS_COLUMN_HEADINGS_PASSWORD = I18n.get("JPanelDatabaseDetailsScreen.tableOptimizeDBDetailsColumnHeadings.Password"); //$NON-NLS-1$
	private static final String TABLE_OPTIMIZE_DB_DETAILS_COLUMN_HEADINGS_USERNAME = I18n.get("JPanelDatabaseDetailsScreen.tableOptimizeDBDetailsColumnHeadings.Username"); //$NON-NLS-1$
	private static final String TABLE_OPTIMIZE_DB_DETAILS_COLUMN_HEADINGS_DATABASE_URL = I18n.get("JPanelDatabaseDetailsScreen.tableOptimizeDBDetailsColumnHeadings.DatabaseURL"); //$NON-NLS-1$
	private static final String TABLE_OPTIMIZE_DB_DETAILS_COLUMN_HEADINGS_COMPONENT = I18n.get("JPanelDatabaseDetailsScreen.tableOptimizeDBDetailsColumnHeadings.Component"); //$NON-NLS-1$
	
	private static final String TABLE_JDBC_POOLS_COLUMN_HEADINGS_CONNECTION_STATUS = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.ConnectionStatus"); //$NON-NLS-1$
	private static final String TABLE_JDBC_POOLS_COLUMN_HEADINGS_FOR_INDEX = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.ForIndex"); //$NON-NLS-1$
	private static final String TABLE_JDBC_POOLS_COLUMN_HEADINGS_FOR_DATA = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.ForData"); //$NON-NLS-1$
	private static final String TABLE_JDBC_POOLS_COLUMN_HEADINGS_USE_DEFAULT = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.UseDefault"); //$NON-NLS-1$
	private static final String TABLE_JDBC_POOLS_COLUMN_HEADINGS_PASSWORD = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.Password"); //$NON-NLS-1$
	private static final String TABLE_JDBC_POOLS_COLUMN_HEADINGS_USERNAME = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.Username"); //$NON-NLS-1$
	private static final String TABLE_JDBC_POOLS_COLUMN_HEADINGS_DATABASE_URL = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.DatabaseURL"); //$NON-NLS-1$
	private static final String TABLE_JDBC_POOLS_COLUMN_HEADINGS_JDBC_POOL = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.JDBCPool"); //$NON-NLS-1$
	private static final String TABLE_JDBC_POOLS_COLUMN_HEADINGS_DB_TYPE = I18n.get("JPanelDatabaseDetailsScreen.tableJDBCPoolsColumnHeadings.DBType"); 
	
	
	private final static String LOAD_TEXT_FORJ_CHECK_BOX = I18n.get("JPanelDatabaseDetailsScreen.LoadTextForjCheckBox"); //$NON-NLS-1$
	
	private static final String TABLESPACE_FOR_INDEX_DEFAULT = "WEBMINDX";
	private static final String TABLESPACE_FOR_DATA_DEFAULT = "WEBMDATA";
	
	//START: Constants for Usability specific to JPanelDatabaseDetailsScreen
	private static final Dimension DIMENSION_J_SCROLL_PANE_OPTIMIZE_DB_DETAILS_TABLE = Usability.getDimension("JPanelDatabaseDetailsScreen.jScrollPaneOptimizeDBDetailsTable.Dimension");
	private static final Dimension DIMENSION_J_SCROLL_PANE_JDBC_POOLS_TABLE = Usability.getDimension("JPanelDatabaseDetailsScreen.jScrollPaneJDBCPoolsTable.Dimension");
	private static final Dimension DIMENSION_J_PANEL_JDBC_POOLS = Usability.getDimension("JPanelDatabaseDetailsScreen.jPanelJDBCPools.Dimension");
	private static final Dimension DIMENSION_J_PANEL_OPTIMZE_DB_DETAILS = Usability.getDimension("JPanelDatabaseDetailsScreen.jPanelOptimzeDBDetails.Dimension");
	private static final Dimension DIMENSION_J_PANEL_DATABASE_ORIGINAL_OR_CLONED = Usability.getDimension("JPanelDatabaseDetailsScreen.jPanelDatabaseOriginalOrCloned.Dimension");
	
	private static final Font FONT_J_TABLE_HEADER = Usability.getFont("JTableHeaderFont");
	private static final Font FONT_J_PANEL_BORDER_TITLE = Usability.getFont("JPanelBorderTitleFont");
	//END: Constants for Usability specific to JPanelDatabaseDetailsScreen

	private static String[] tableJDBCPoolsHeadings = new String[] { 
			TABLE_JDBC_POOLS_COLUMN_HEADINGS_JDBC_POOL,TABLE_JDBC_POOLS_COLUMN_HEADINGS_DB_TYPE, J_CHECKBOX_DATABASE_UPGRADE_DATABASE_ACTION,
			TABLE_JDBC_POOLS_COLUMN_HEADINGS_DATABASE_URL, TABLE_JDBC_POOLS_COLUMN_HEADINGS_USERNAME, TABLE_JDBC_POOLS_COLUMN_HEADINGS_PASSWORD, TABLE_JDBC_POOLS_COLUMN_HEADINGS_USE_DEFAULT, TABLE_JDBC_POOLS_COLUMN_HEADINGS_FOR_DATA,
			TABLE_JDBC_POOLS_COLUMN_HEADINGS_FOR_INDEX, TABLE_JDBC_POOLS_COLUMN_HEADINGS_CONNECTION_STATUS };
	
	private static Vector<String> tableJDBCPoolsHeadingsVector = new Vector<String>(Arrays.asList(tableJDBCPoolsHeadings));
	
	private int[] columnWidths = new int[] { 115, 60, 45, 85, 60, 60, 85, 60, 60, 70 };
	
	/////////////Central configuration and central user are same
	public static enum DatabaseComponentName {
		ARCHIVING, DOCUMENTHISTORY, MYWEBMETHODSSERVER, ISCOREAUDIT, ISINTERNAL, DISTRIBUTEDLOCKING, PROCESSAUDIT, PROCESSENGINE, TRADINGNETWORKS, TRADINGNETWORKSARCHIVE, CROSSREFERENCE, REPORTING, STAGING, CENTRALCONFIGURATION, ANALYSIS, PROCESSTRACKER, DATAPURGE, ONEDATAMETADATA, ONEDATAWORKAREA, ONEDATARELEASEAREA, ACTIVETRANSFER, BUSINESSRULES
	}
	
	public static enum JDBCPoolName {
		CENTRALUSERS, ARCHIVING, DOCUMENTHISTORY, ISCOREAUDIT, ISINTERNAL, PROCESSAUDIT, PROCESSENGINE, TN, DATAPURGE, DISTRIBUTEDLOCKING, XREF, REPORTING, STAGING, ANALYSIS, PROCESSTRACKER, ONEDATAMETADATA, ONEDATAWORKAREA, ONEDATARELEASEAREA, ACTIVETRANSFER, BUSINESSRULES
	}
	
	String workingDirectory = PropertiesFiles.absolutePathOfFile;
	File dbTablesContents = new File(workingDirectory + "/resources/JdbcPoolsTableContents.csv"); //$NON-NLS-1$
	
	URL passIconURL = getClass().getResource(getCommonProperty("AbsolutePathOfPassIconImage"));
	URL failIconURL = getClass().getResource(getCommonProperty("AbsolutePathOfFailIconImage")); //$NON-NLS-1$
	URL warningIconURL = getClass().getResource(getCommonProperty("AbsolutePathOfWarningIconImage")); //$NON-NLS-1$
	URL unknownIconURL = getClass().getResource(getCommonProperty("AbsolutePathOfUnknownIconImage"));
	
	ImageIcon passIcon = new ImageIcon(passIconURL);
	ImageIcon failIcon = new ImageIcon(failIconURL);
	ImageIcon warningIcon = new ImageIcon(warningIconURL);
	ImageIcon unknownIcon = new ImageIcon(unknownIconURL);

	//START: CONSTANTS
	final static String RELEASE65 = I18n.get("Release65");
	final static String RELEASE712 = I18n.get("Release712");
	final static String RELEASE713 = I18n.get("Release713");
	final static String RELEASE80 = I18n.get("Release801");
	final static String RELEASE822 = I18n.get("Release822");
	final static String RELEASE90 = I18n.get("Release90");
	final static String RELEASE95 = I18n.get("Release95");
	final static String RELEASESELECT = I18n.get("comboBox_Option_Select");
	final static String ORACLE_RDBMS = "Oracle";
	final static String SQLSERVER_RDBMS = "Sql Server";
	
	//END: CONSTANTS
	
	static MyTableModelNew myTableModelNewJDBCPoolTable, myTableModelNewOptimizeTable;
	
	/**
	 * Creates new form testThirdScreen
	 */
	public JPanelDatabaseDetailsScreen() {
		initComponents();
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		buttonGroupTargetDatabase = new javax.swing.ButtonGroup();
		jRadioButtonTargetDatabaseClone = new javax.swing.JRadioButton();
	    jRadioButtonTargetDatabaseExisting = new javax.swing.JRadioButton();
	    
	    jComboBoxRDBMS = new javax.swing.JComboBox();
	    
		jPanelJDBCPools = new javax.swing.JPanel();
		jPanelBottomPanel = new javax.swing.JPanel();
		jSeparatorBottomPanel1 = new javax.swing.JSeparator();
		jButtonNext = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();
		jButtonBack = new javax.swing.JButton();
		jPanelHeading = new javax.swing.JPanel();
		jPanelMain = new javax.swing.JPanel();
		jPanelDB= new javax.swing.JPanel();
		jPanelBanner = CommonUI.prepareBanner(jPanelMain);
		jPanelDatabaseOriginalOrCloned =  new javax.swing.JPanel();
		jPanelRDBMS =  new javax.swing.JPanel();
		jScrollPaneJDBCPoolsTable = new javax.swing.JScrollPane();

		jTextAreaAboutTableSpaceDescription = new javax.swing.JTextArea();
		jTextAreaAboutTableSpaceDescription.setFont(Usability.FONT_TEXT_AREA); //$NON-NLS-1$
		Color m_color = UIManager.getColor ( Usability.PANEL_BACKGROUND ); //$NON-NLS-1$
		jTextAreaAboutTableSpaceDescription.setBackground(m_color);
		jTextAreaAboutTableSpaceDescription.setPreferredSize(Usability.getDimension("JPanelDatabaseDetailsScreen.jTextAreaAboutTableSpaceDescription.Dimension"));
		jTextAreaAboutTableSpaceDescription.setText(J_TEXT_AREA_ABOUT_TABLE_SPACE_DESCRIPTION); //$NON-NLS-1$
		jTextAreaAboutTableSpaceDescription.setLineWrap(true);
		jTextAreaAboutTableSpaceDescription.setWrapStyleWord(true);
		jTextAreaAboutTableSpaceDescription.setColumns(20);
		jTextAreaAboutTableSpaceDescription.setRows(1);
		jTextAreaAboutTableSpaceDescription.setBorder(null);
		jTextAreaAboutTableSpaceDescription.setAutoscrolls(false);
		jTextAreaAboutTableSpaceDescription.setEditable(false);
		jTextAreaAboutTableSpaceDescription.setVisible(false);
		
		jLabelOfHeadline = new javax.swing.JLabel();
		jLabelOfDatabaseUpgradeType = new javax.swing.JLabel();
		jLabelOfRDBMS = new javax.swing.JLabel();
		jLableOfDescriptionTextOfHeadline = new javax.swing.JLabel();
		jSeparatorHeading = new javax.swing.JSeparator();
		
		jComboBoxRDBMS.setFont(Usability.FONT_ELEMENTTEXT); //$NON-NLS-1$
		jComboBoxRDBMS.setModel(new javax.swing.DefaultComboBoxModel(new String[] {ORACLE_RDBMS, SQLSERVER_RDBMS}));
		jComboBoxRDBMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRDBMSActionPerformed(evt);
            }
        });
		
		jButtonTestConnection = new javax.swing.JButton();
		jButtonTestConnection.setText(J_BUTTON_TEST_CONNECTION); //$NON-NLS-1$
		jButtonTestConnection.setFont(Usability.FONT_BUTTON); //$NON-NLS-1$
		
		jPanelDB.setVisible(false);
		jPanelJDBCPools.setBorder(javax.swing.BorderFactory.createTitledBorder(J_PANEL_DATABASE_COMPONENTS));
		jPanelJDBCPools.setFont(FONT_J_PANEL_BORDER_TITLE); //$NON-NLS-1$
		
		jTableJDBCPools = new JTableDataBase();
		jTableJDBCPools.setVisible(true);
		jTableJDBCPools.setFont(Usability.FONT_TEXT_AREA);
		
		// Setting TableModels to two tables
		myTableModelNewJDBCPoolTable = new MyTableModelNew(tableJDBCPoolsHeadingsVector);
		jTableJDBCPools.setModel(myTableModelNewJDBCPoolTable);
		
		// For spliting the column Header of table of jTableJDBCPools
		TableColumnModel coloumnModelJDBCPools = jTableJDBCPools.getColumnModel();
		
		ColumnGroup g_nameJDBCPools = new ColumnGroup(COLUMN_GROUPS);
		
		g_nameJDBCPools.add(coloumnModelJDBCPools.getColumn(6));
		g_nameJDBCPools.add(coloumnModelJDBCPools.getColumn(7));
		g_nameJDBCPools.add(coloumnModelJDBCPools.getColumn(8));
		g_nameJDBCPools.setHeaderRenderer(new CustomHeaderRenderer());
		
		GroupableTableHeader headerJDBCPools = (GroupableTableHeader) jTableJDBCPools.getTableHeader();
		headerJDBCPools.addColumnGroup(g_nameJDBCPools);

		//To set Column 3 (JDBC URL) Renderer
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText(HTML_DBURL_TOOLTIP);
		jTableJDBCPools.getColumnModel().getColumn(3).setCellRenderer(renderer);
		
		// To set column 5 (Password) Renderer and editor for cells and Renderer for Header.
		DefaultCellEditor passwordEditor = new DefaultCellEditor(new JPasswordField());
		jTableJDBCPools.getColumnModel().getColumn(5).setCellEditor(passwordEditor);
		jTableJDBCPools.getColumnModel().getColumn(5).setCellRenderer(new PasswordCellRenderer(jTableJDBCPools, 5));

		//To set column 6 (Use Default) Renderer for Header.
		TableColumn tableColumnUseDefaultjTableJDBCPools = jTableJDBCPools.getColumnModel().getColumn(6);
		tableColumnUseDefaultjTableJDBCPools.setHeaderRenderer(new CheckBoxHeader(new jTableJDBCPoolsUseDefaultHeaderItemListener()));

		//Add Action column's cell editors and renderers
		TableColumn jTableJDBCPoolsActionTableColumn = jTableJDBCPools.getColumnModel().getColumn(2);
		setUpComboboxColumn(jTableJDBCPoolsActionTableColumn);
		
		//Add DB Type column's cell editors and renderers
		TableColumn jTableJDBCPoolsDBTypeTableColumn = jTableJDBCPools.getColumnModel().getColumn(1);
		setUpDBTypeComboboxColumn(jTableJDBCPoolsDBTypeTableColumn);
		
		// To set column 9 (Status) Editor for column cells and Renderer for column Header.
		jTableJDBCPools.getColumnModel().getColumn(9).setCellEditor(new ButtonColumn(jTableJDBCPools, new ActionListenerLastColumnjTableJDBCPools(),9));
		
		jTableJDBCPools.getColumnModel().getColumn(9).setHeaderRenderer(new CustomHeaderRenderer());
		
		/** START:Set the UI of two tables ********************************************************************************/
		// Sets the rows size of table
		jTableJDBCPools.setRowHeight(18);
		
		//Sets header font
		jTableJDBCPools.getTableHeader().setFont(FONT_J_TABLE_HEADER);
		
		//Allowing the auto resize of the subsequent columns
		jTableJDBCPools.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		
		// setting the table header height
		jTableJDBCPools.getTableHeader().setPreferredSize(new Dimension(jTableJDBCPools.getTableHeader().getWidth(), 52));

		// To set the column widths for table jTableJDBCPools
		for (int i = 0; i < columnWidths.length; i++) {
			TableColumn col = jTableJDBCPools.getColumnModel().getColumn(i);
			col.setPreferredWidth(columnWidths[i]);
		}
		/** END:Set the UI of two tables ********************************************************************************/
		
		setPreferredSize(Usability.SIZE_SCREENPANEL);
		jPanelMain.setPreferredSize(Usability.SIZE_JPANELMAIN);
		
		jPanelJDBCPools.setPreferredSize(DIMENSION_J_PANEL_JDBC_POOLS);
		jScrollPaneJDBCPoolsTable.setPreferredSize(DIMENSION_J_SCROLL_PANE_JDBC_POOLS_TABLE);
		jPanelBottomPanel.setPreferredSize(Usability.SIZE_JPANELBOTTOM);
		
		jScrollPaneJDBCPoolsTable.setViewportView(jTableJDBCPools);
		jScrollPaneJDBCPoolsTable.setBorder(BorderFactory.createEmptyBorder());
		
		jLabelOfHeadline.setFont(Usability.FONT_HEADLINELABEL); 
		jLabelOfHeadline.setText(J_LABEL_OF_HEADLINE); //$NON-NLS-1$
		
		jLabelOfDatabaseUpgradeType.setFont(Usability.FONT_HEADLINELABEL); 
		jLabelOfDatabaseUpgradeType.setText(J_LABEL_OF_DATABASE_UPGRADE_TYPE); 
		
		jLabelOfRDBMS.setFont(Usability.FONT_HEADLINELABEL);
		jLabelOfRDBMS.setText(J_LABEL_OF_DATABASE_RDBMS);
		
		jLableOfDescriptionTextOfHeadline.setFont(Usability.FONT_HEADLINEDESCRIPTIONLABEL); //$NON-NLS-1$
		jLableOfDescriptionTextOfHeadline.setText(J_LABLE_OF_DESCRIPTION_TEXT_OF_HEADLINE); //$NON-NLS-1$
		
		jCheckBoxLoadAndSave = new javax.swing.JCheckBox();
		jCheckBoxLoadAndSave.setText(LOAD_TEXT_FORJ_CHECK_BOX);
		jCheckBoxLoadAndSave.setFont(Usability.FONT_TEXT_AREA);

		jButtonTestConnection
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						jButtonTestConnectionActionPerformed(evt);
					}
				});
		
		jCheckBoxLoadAndSave.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				jCheckBoxLoadAndSaveActionPerformed(evt);
			}
		});

		jButtonNext.setFont(Usability.FONT_BUTTON_WIZARD_NAVIGATION);
		jButtonNext.setText(J_BUTTON_NEXT); //$NON-NLS-1$
		jButtonNext.setEnabled(false);
		jButtonNext.setPreferredSize(Usability.SIZE_BUTTON_BOTTOMPANEL);
		jButtonNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButtonNextActionPerformed(evt);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		jButtonCancel.setFont(Usability.FONT_BUTTON_WIZARD_NAVIGATION); // NOI18N //$NON-NLS-1$
		jButtonCancel.setText(J_BUTTON_CANCEL); //$NON-NLS-1$
		jButtonCancel.setPreferredSize(Usability.SIZE_BUTTON_BOTTOMPANEL);
		jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonCancelActionPerformed(evt);
			}
		});

		jButtonBack.setFont(Usability.FONT_BUTTON_WIZARD_NAVIGATION); // NOI18N //$NON-NLS-1$
		jButtonBack.setText(J_BUTTON_BACK); //$NON-NLS-1$
		jButtonBack.setPreferredSize(Usability.SIZE_BUTTON_BOTTOMPANEL);
		jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonBackActionPerformed(evt);
			}
		});
		
		buttonGroupTargetDatabase.add(jRadioButtonTargetDatabaseClone);
		jRadioButtonTargetDatabaseClone.setFont(Usability.FONT_ELEMENTTEXT); // NOI18N //$NON-NLS-1$
		jRadioButtonTargetDatabaseClone.setText(J_RADIO_BUTTON_DATABASE_UPGRADE_CLONE); //$NON-NLS-1$
		jRadioButtonTargetDatabaseClone.setEnabled(true);
		jRadioButtonTargetDatabaseClone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonDataBaseUpgradeCloneActionPerformed(evt);
            }
        });
		
		 buttonGroupTargetDatabase.add(jRadioButtonTargetDatabaseExisting);
		 jRadioButtonTargetDatabaseExisting.setFont(Usability.FONT_ELEMENTTEXT); // NOI18N //$NON-NLS-1$
		 jRadioButtonTargetDatabaseExisting.setText(J_RADIO_BUTTON_DATABASE_UPGRADE_EXISTING); //$NON-NLS-1$
		 jRadioButtonTargetDatabaseExisting.setEnabled(true);
		 jRadioButtonTargetDatabaseExisting.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jRadioButtonDataBaseUpgradeExistingActionPerformed(evt);
	            }
	        });
		javax.swing.GroupLayout jPanelBottomPanelLayout = new javax.swing.GroupLayout(
				jPanelBottomPanel);
		jPanelBottomPanel.setLayout(jPanelBottomPanelLayout);
		jPanelBottomPanelLayout
				.setHorizontalGroup(jPanelBottomPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelBottomPanelLayout
										.createSequentialGroup()

										.addGroup(
												jPanelBottomPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jSeparatorBottomPanel1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																612,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanelBottomPanelLayout
																		.createSequentialGroup()
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)
																		.addComponent(
																				jButtonCancel,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(20,
																				20,
																				20)
																		.addComponent(
																				jButtonBack,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(10,
																				10,
																				10)
																		.addComponent(
																				jButtonNext,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(20,
																				20,
																				20))
										)));
		jPanelBottomPanelLayout
				.setVerticalGroup(jPanelBottomPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelBottomPanelLayout
										.createSequentialGroup()
										.addComponent(
												jSeparatorBottomPanel1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												0, Short.MAX_VALUE)
										.addGap(20, 20, 20)
										.addGroup(
												jPanelBottomPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jButtonCancel)
														.addComponent(
																jButtonBack)
														.addComponent(
																jButtonNext))
										.addGap(20, 20, 20)));

		javax.swing.GroupLayout jPanelHeadingLayout = new javax.swing.GroupLayout(
				jPanelHeading);
		jPanelHeading.setLayout(jPanelHeadingLayout);
		jPanelHeadingLayout
				.setHorizontalGroup(jPanelHeadingLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelHeadingLayout
										.createSequentialGroup()
										.addGroup(
												jPanelHeadingLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jLabelOfHeadline,
																GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jLableOfDescriptionTextOfHeadline,
																GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jSeparatorHeading)
																)));
		jPanelHeadingLayout
				.setVerticalGroup(jPanelHeadingLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelHeadingLayout
										.createSequentialGroup()
										.addGroup(
												jPanelHeadingLayout
														.createSequentialGroup()
														.addComponent(
																jLabelOfHeadline)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(
																jLableOfDescriptionTextOfHeadline)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(
																jSeparatorHeading)
														.addGap(5, 5, 5))));
		//JPanel for RDBMS selection
		javax.swing.GroupLayout jPanelRDBMSLayout = new javax.swing.GroupLayout(jPanelRDBMS);
		jPanelRDBMS.setLayout(jPanelRDBMSLayout);
		jPanelRDBMSLayout.setHorizontalGroup(
			jPanelRDBMSLayout.createSequentialGroup()
				.addComponent(jLabelOfRDBMS)
				.addComponent(jComboBoxRDBMS,javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
			);
		jPanelRDBMSLayout.setVerticalGroup(
			jPanelRDBMSLayout.createSequentialGroup()
			.addGroup(jPanelRDBMSLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			.addComponent(jLabelOfRDBMS)
			.addComponent(jComboBoxRDBMS)
			)
			);
		
		//JPanel for radio button of Existing and Clone
		javax.swing.GroupLayout jPanelDatabaseOriginalOrClonedLayout = new javax.swing.GroupLayout(jPanelDatabaseOriginalOrCloned);
		jPanelDatabaseOriginalOrCloned.setLayout(jPanelDatabaseOriginalOrClonedLayout);
		jPanelDatabaseOriginalOrClonedLayout.setHorizontalGroup(
			jPanelDatabaseOriginalOrClonedLayout.createSequentialGroup()
				  .addComponent(jLabelOfDatabaseUpgradeType)
			      .addComponent(jRadioButtonTargetDatabaseExisting)
			      .addComponent(jRadioButtonTargetDatabaseClone)
			);
		jPanelDatabaseOriginalOrClonedLayout.setVerticalGroup(
			jPanelDatabaseOriginalOrClonedLayout.createSequentialGroup()
			.addGroup(jPanelDatabaseOriginalOrClonedLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				  .addComponent(jLabelOfDatabaseUpgradeType)
				  .addComponent(jRadioButtonTargetDatabaseExisting)
				  .addComponent(jRadioButtonTargetDatabaseClone)
				  )
			);

		javax.swing.GroupLayout jPanelJDBCPoolsLayout = new javax.swing.GroupLayout(
				jPanelJDBCPools);
		jPanelJDBCPools.setLayout(jPanelJDBCPoolsLayout);
		jPanelJDBCPoolsLayout.setHorizontalGroup(jPanelJDBCPoolsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelJDBCPoolsLayout.createSequentialGroup()
								.addComponent(jScrollPaneJDBCPoolsTable, 0,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
				));

		jPanelJDBCPoolsLayout.setVerticalGroup(jPanelJDBCPoolsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelJDBCPoolsLayout.createSequentialGroup()
								.addComponent(jScrollPaneJDBCPoolsTable, 0,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
				));
// start jPanelDB================================================================================================
		
		
		javax.swing.GroupLayout jPanelDBLayout = new javax.swing.GroupLayout(
				jPanelDB);
		jPanelDB.setLayout(jPanelDBLayout);
		jPanelDBLayout
				.setHorizontalGroup(jPanelDBLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelDBLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jPanelJDBCPools, 0,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												jPanelDBLayout
														.createSequentialGroup()
														.addComponent(jCheckBoxLoadAndSave,0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 20, 500)
														.addComponent(
																jButtonTestConnection)
														// Gap is needed to be
														// in parallel with the
														// next button
														.addGap(20)))
												);
		jPanelDBLayout
				.setVerticalGroup(jPanelDBLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelDBLayout
										.createSequentialGroup()
										.addComponent(jPanelJDBCPools, 0,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(jPanelDBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																	.addComponent(jCheckBoxLoadAndSave)
																	.addComponent(jButtonTestConnection)
												)
																));
		
		
		
		
		// end jPanelDB==========================================================================================================
		javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(
				jPanelMain);
		jPanelMain.setLayout(jPanelMainLayout);
		jPanelMainLayout
				.setHorizontalGroup(jPanelMainLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jPanelHeading, 0,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//						.addGroup(arg0)
						.addComponent(jPanelDatabaseOriginalOrCloned, 0,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jPanelRDBMS, 0, 
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jPanelDB, 0,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								jPanelMainLayout
										.createSequentialGroup()
										.addComponent(
												jTextAreaAboutTableSpaceDescription,
												0, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jPanelMainLayout
				.setVerticalGroup(jPanelMainLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanelMainLayout
										.createSequentialGroup()
										.addComponent(
												jPanelHeading,
												0,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												60)
//										.addComponent(arg0)
										.addComponent(jPanelDatabaseOriginalOrCloned,
												0, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(jPanelRDBMS, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jPanelDB,
												0, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanelMainLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jTextAreaAboutTableSpaceDescription))));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jPanelBanner,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														634, Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(20)
																.addComponent(
																		jPanelMain,
																		0,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addGap(20))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(20)
																.addComponent(
																		jPanelBottomPanel,
																		0,
																		632,
																		Short.MAX_VALUE)
																.addGap(20))
								)
				));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jPanelBanner,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(20)
								.addComponent(jPanelMain, 0,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										0, Short.MAX_VALUE)
								.addComponent(jPanelBottomPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(20)));

		// To show the Optmize DB table depending on the selection, Uncomment
		// ONLY when this class is individually run.
		 /*showOrHideOptimizeDBTable(); 
		 generateJDBCPoolsTable();*/
		
	}// END: initComponents()
	
	private void jComboBoxRDBMSActionPerformed(java.awt.event.ActionEvent evt){
		JComboBox m_cb = (JComboBox) evt.getSource();
        String m_RDBMSChoice = (String) m_cb.getSelectedItem();
        Inputs.setRDBMS(m_RDBMSChoice);
        
        generateJDBCPoolsTable();
        showJPanelDBDetils();
	}
	
	/**
	 * @author chm
	 * Action class for the cell/button of last column of the table - jTableJDBCPools
	 * When the button/cell of the column is clicked, actionPerformed method will be called.  
	 * 
	 * This need to be inline class since "jButtonLastColumnjTableJDBCPoolsActionPerformed" 
	 * is called within it.
	 */
	class ActionListenerLastColumnjTableJDBCPools implements Action {

		@Override
		public void actionPerformed(ActionEvent evt) {
			jButtonLastColumnjTableJDBCPoolsActionPerformed(evt);
		}

		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public void putValue(String key, Object value) {
		}

		@Override
		public void setEnabled(boolean b) {
		}

		@Override
		public boolean isEnabled() {
			return false;
		}

		@Override
		public void addPropertyChangeListener(
				PropertyChangeListener listener) {
		}

		@Override
		public void removePropertyChangeListener(
				PropertyChangeListener listener) {
		}
	}
	
	/**
	 * To set the default values to "for data" and "for index" columns when "Use default" Header check box is clicked.
	 * This is for table jTableJDBCPools
	 */
	class jTableJDBCPoolsUseDefaultHeaderItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			Object source = e.getSource();
			if (source instanceof AbstractButton == false) {
				return;
			}
			boolean checked = e.getStateChange() == ItemEvent.SELECTED;
			for (int x = 0, y = jTableJDBCPools.getRowCount(); x < y; x++) {
				jTableJDBCPools.setValueAt(new Boolean(checked), x, 6);
				if (checked) {
					jTableJDBCPools.setValueAt(TABLESPACE_FOR_INDEX_DEFAULT, x, 7);
					jTableJDBCPools.setValueAt(TABLESPACE_FOR_DATA_DEFAULT, x, 8);
				} else {
					jTableJDBCPools.setValueAt(EMPTY_STRING, x, 7); //$NON-NLS-1$
					jTableJDBCPools.setValueAt(EMPTY_STRING, x, 8); //$NON-NLS-1$
				}
			}
		}
	}
	
//	/**
//	 * To set the default values to "for data" and "for index" columns when "Use default" Header check box is clicked.
//	 * This is for table jTableJDBCPools
//	 */
//	class jTableJDBCPoolsUseDefaultCellItemListener implements ItemListener {
//		public void itemStateChanged(ItemEvent e) {
//			Object source = e.getSource();
//			if (source instanceof AbstractButton == false) {
//				return;
//			}
//			CheckBoxCell checkBoxCell = (CheckBoxCell) source;
//			int x = checkBoxCell.getRow();
////			System.out.println("Row           ::::::::::::::::" + x);
//			
//			boolean checked = e.getStateChange() == ItemEvent.SELECTED;
//			jTableJDBCPools.setValueAt(new Boolean(checked), x, 6);
//			if (checked) {
//				jTableJDBCPools.setValueAt(TABLESPACE_FOR_INDEX_DEFAULT, x, 7);
//				jTableJDBCPools.setValueAt(TABLESPACE_FOR_DATA_DEFAULT, x, 8);
//			} else {
//				jTableJDBCPools.setValueAt(EMPTY_STRING, x, 7);
//				jTableJDBCPools.setValueAt(EMPTY_STRING, x, 8);
//			}
//		}
//	}
//	
//	/**
//	 * To set the default values to "for data" and "for index" columns when "Use default" Header check box is clicked.
//	 * This is for table jTableOptimizeDBDetails
//	 */
//	class jTableOptimizeDBDetailsUseDefaultCellItemListener implements ItemListener {
//		public void itemStateChanged(ItemEvent e) {
//			Object source = e.getSource();
//			if (source instanceof AbstractButton == false) {
//				return;
//			}
//			boolean checked = e.getStateChange() == ItemEvent.SELECTED;
//			CheckBoxCell checkBoxCell = (CheckBoxCell) source;
//			int x = checkBoxCell.getRow();
//			
//			jTableOptimizeDBDetails.setValueAt(new Boolean(checked), x,	6);
//			if (checked) {
//				jTableOptimizeDBDetails.setValueAt(TABLESPACE_FOR_DATA_DEFAULT, x, 7);
//				jTableOptimizeDBDetails.setValueAt(TABLESPACE_FOR_INDEX_DEFAULT, x, 8);
//			} else {
//				jTableOptimizeDBDetails.setValueAt(EMPTY_STRING, x, 7); //$NON-NLS-1$
//				jTableOptimizeDBDetails.setValueAt(EMPTY_STRING, x, 8); //$NON-NLS-1$
//			}
//		}
//	}
//	
//	/**
//	 * Item listener for migrate and create.
//	 * This is for table jTableJDBCPools
//	 */
//	class jTableJDBCPoolsMigrateCreateCellItemListener implements ItemListener {
//		
//		public void itemStateChanged(ItemEvent e) {
//			Object source = e.getSource();
//			if (source instanceof AbstractButton == false) {
//				return;
//			}
//			CheckBoxCell checkBoxCell = (CheckBoxCell) source;
//			int rowNum = checkBoxCell.getRow();
//			int columnNum = checkBoxCell.getColumn();
//			
//			System.out.println("Row: " + rowNum + "Column: " + columnNum );
//			
//			boolean checked = e.getStateChange() == ItemEvent.SELECTED;
//			jTableJDBCPools.setValueAt(new Boolean(checked), rowNum, columnNum);
//			if (columnNum == 1) {
//				if (checked) {
//					jTableJDBCPools.setValueAt(false, rowNum, 2);
//					if(jRadioButtonTargetDatabaseExisting.isSelected()){
//						String jdbcPoolName= (String)jTableJDBCPools.getValueAt(rowNum, 0);
//						if(jdbcPoolName.equalsIgnoreCase("MywebMethodsServer")){
//							jdbcPoolName = "CentralUsers";
//						}
//						String[] dbNameUserIdPassword = getJDBCDBParameters(jdbcPoolName);
//						
//						jTableJDBCPools.setValueAt(dbNameUserIdPassword[0], rowNum,3);
//						jTableJDBCPools.setValueAt(dbNameUserIdPassword[1], rowNum,4);
//						jTableJDBCPools.setValueAt(dbNameUserIdPassword[2], rowNum,5);
//					}
//				} else {
//					jTableJDBCPools.setValueAt(true, rowNum, 2);
//					jTableJDBCPools.setValueAt("", rowNum,3);
//					jTableJDBCPools.setValueAt("", rowNum,4);
//					jTableJDBCPools.setValueAt("", rowNum,5);
//				}
//			}
//			else if (columnNum == 2) {
//				if (checked) {
//					jTableJDBCPools.setValueAt(false, rowNum, 1);
//					
//					jTableJDBCPools.setValueAt("", rowNum,3);
//					jTableJDBCPools.setValueAt("", rowNum,4);
//					jTableJDBCPools.setValueAt("", rowNum,5);
//					
//				} else {
//					jTableJDBCPools.setValueAt(true, rowNum, 1);
//					if(jRadioButtonTargetDatabaseExisting.isSelected()){
//						
//						String[] dbNameUserIdPassword = getJDBCDBParameters((String)jTableJDBCPools.getValueAt(rowNum, 0));
//						
//						jTableJDBCPools.setValueAt(dbNameUserIdPassword[0], rowNum,3);
//						jTableJDBCPools.setValueAt(dbNameUserIdPassword[1], rowNum,4);
//						jTableJDBCPools.setValueAt(dbNameUserIdPassword[2], rowNum,5);
//					}
//				}
//			}
//		}
//	}
//	
//	/**
//	 * Item listener for migrate and create.
//	 * This is for table jTableOptimizeDBDetails
//	 */
//	class jTableOptimizeDBDetailsMigrateCreateCellItemListener implements ItemListener {
//		public void itemStateChanged(ItemEvent e) {
//			Object source = e.getSource();
//			if (source instanceof AbstractButton == false) {
//				return;
//			}
//			CheckBoxCell checkBoxCell = (CheckBoxCell) source;
//			int rowNum = checkBoxCell.getRow();
//			int columnNum = checkBoxCell.getColumn();
//			
//			System.out.println("Row: " + rowNum + "Column: " + columnNum );
//			
//			boolean checked = e.getStateChange() == ItemEvent.SELECTED;
//			
//			jTableOptimizeDBDetails.setValueAt(new Boolean(checked), rowNum, columnNum);
//			if (columnNum == 1) {
//				if (checked) {
//					jTableOptimizeDBDetails.setValueAt(false, rowNum, 2);
//				} else {
//					jTableOptimizeDBDetails.setValueAt(true, rowNum, 2);
//				}
//			}
//			else if (columnNum == 2) {
//				if (checked) {
//					jTableOptimizeDBDetails.setValueAt(false, rowNum, 1);
//				} else {
//					jTableOptimizeDBDetails.setValueAt(true, rowNum, 1);
//				}
//			}
//		}
//	}
//	
//	/**
//	 * To set the default values to "for data" and "for index" columns when "Use default" Header check box is clicked.
//	 * This is for table jTableJDBCPools
//	 */
//	class jTableJDBCPoolsMigrateCellItemListener implements ItemListener {
//		public void itemStateChanged(ItemEvent e) {
//			Object source = e.getSource();
//			if (source instanceof AbstractButton == false) {
//				return;
//			}
//			CheckBoxCell checkBoxCell = (CheckBoxCell) source;
//			int x = checkBoxCell.getRow();
//			
//			boolean checked = e.getStateChange() == ItemEvent.SELECTED;
//			jTableJDBCPools.setValueAt(new Boolean(checked), x, 6);
//			if (checked) {
//				jTableJDBCPools.setValueAt(false, x, 2);
//			} else {
//				jTableJDBCPools.setValueAt(true, x, 2);
//			}
//		}
//	}
	
	/**
	 * Check for the <JDBCPool>.xml file presence and get the connection pool alias name, if present inside <JDBCPool>.xml file. 
	 * Then again check for the <connection name>.xml file presence
	 * @param absolutePathOfJDBCPool
	 * @param absolutePathOfConnectionAliasDirectory
	 * @param isPasswordHandlePrefix
	 * @param productDirectoryName
	 * @param rowIndex
	 */
	public static ArrayList<String> getJDBCDBParametersWithOutPopUps(JDBCPoolName jdbcPoolName){
		String m_installDir = Inputs.getSourceInstallationDirectory();
		
		String absolutePathOfJDBCPool = "/IntegrationServer/config/jdbc/function/"+ jdbcPoolName.toString() +".xml";
		String absolutePathOfConnectionAliasDirectory = "/IntegrationServer/config/jdbc/pool/";
		String isPasswordHandlePrefix = "wm.is.admin.jdbc.";
		String productDirectoryName = "IntegrationServer";
		String m_searchConnPoolString = "connPoolAlias\">";
	
		String message="";
		String m_connectionPoolAliasName = "";
		String m_JDBCPoolFileRelativePath = m_installDir + absolutePathOfJDBCPool;
		ArrayList<String> dbNameUserIdPassword = new ArrayList<String>();
		dbNameUserIdPassword.add("");
		dbNameUserIdPassword.add("");
		dbNameUserIdPassword.add("");
		
		try{
		 m_connectionPoolAliasName = JPanelPreUpgradeStepsScreen.getConnectionPoolAlias(m_JDBCPoolFileRelativePath, m_searchConnPoolString);
		 if(m_connectionPoolAliasName.equalsIgnoreCase(EMPTY_STRING)){
				message="The associated connection pool alias for this Functional Alias Name not found. Please enter details manually if the JDBC component exist and you want to migrate.";
			}
			else{
				String m_ConnectionAliasPoolFileRelativePath = m_installDir + absolutePathOfConnectionAliasDirectory + m_connectionPoolAliasName + ".xml";
				dbNameUserIdPassword=UpdateJDBCDBParameters.getDbNameUseridPassword(isPasswordHandlePrefix, m_connectionPoolAliasName, productDirectoryName, m_ConnectionAliasPoolFileRelativePath);
				return dbNameUserIdPassword;
			}
		}
		catch (IOException e){
			message = "File with Functional Alias name not found. Please enter details manually if the JDBC component exist and you want to migrate.";
		}
		return dbNameUserIdPassword;
	}
	
	public static boolean isDatabaseEmbedded(JDBCPoolName jdbcPoolName){
	
		String m_installDir = Inputs.getSourceInstallationDirectory();
		
		String absolutePathOfJDBCPool = "/IntegrationServer/config/jdbc/function/"+ jdbcPoolName.toString() +".xml";
		String absolutePathOfConnectionAliasDirectory = "/IntegrationServer/config/jdbc/pool/";
		String isPasswordHandlePrefix = "wm.is.admin.jdbc.";
		String productDirectoryName = "IntegrationServer";
		String m_searchConnPoolString = "connPoolAlias\">";
	
		String message="";
		String m_connectionPoolAliasName = "";
		String m_JDBCPoolFileRelativePath = m_installDir + absolutePathOfJDBCPool;
		
		String m_searchJDBCPoolDBUrl = "dbURL\">";
		String[] m_tempJDBCPoolDBUrl;
		String m_delimiterISCoreAuditDBUrl = "</";
		String m_delimeterDBType = ":";
		String[] m_tempJDBCPoolDBType;
		String m_searchISUsr = "userid\">";
		String[] m_tempISCoreAuditusr;
		String m_delimiterISCoreAuditusr = "</";
		StringBuilder m_StringBuildermConnectionAliasPoolFileContents = new StringBuilder();
		
		try{
		 m_connectionPoolAliasName = JPanelPreUpgradeStepsScreen.getConnectionPoolAlias(m_JDBCPoolFileRelativePath, m_searchConnPoolString);
		 if(m_connectionPoolAliasName.equalsIgnoreCase(EMPTY_STRING)){
				message="The associated connection pool alias for this Functional Alias Name not found. Please enter details manually if the JDBC component exist and you want to migrate.";
				return false;
			}
			else{
				String m_ConnectionAliasPoolFileRelativePath = m_installDir + absolutePathOfConnectionAliasDirectory + m_connectionPoolAliasName + ".xml";
				
				try {
					BufferedReader m_BufferReader = new BufferedReader(new FileReader(m_ConnectionAliasPoolFileRelativePath));
					while (m_BufferReader.ready()) {
						m_StringBuildermConnectionAliasPoolFileContents.append(m_BufferReader.readLine());
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				String m_StringCompleteISCoreAuditDBUrl = m_StringBuildermConnectionAliasPoolFileContents
						.substring(m_StringBuildermConnectionAliasPoolFileContents.indexOf(m_searchJDBCPoolDBUrl)+ m_searchJDBCPoolDBUrl.length(),
								m_StringBuildermConnectionAliasPoolFileContents.indexOf(m_searchJDBCPoolDBUrl) + 80);
				m_tempJDBCPoolDBUrl = m_StringCompleteISCoreAuditDBUrl
						.split(m_delimiterISCoreAuditDBUrl);
				String m_dburlJDBCPool = m_tempJDBCPoolDBUrl[0];
				
			//	Inputs.setIsCoreAuditDbUrl(m_dburlJDBCPool);
				
				m_tempJDBCPoolDBType = m_dburlJDBCPool.split(m_delimeterDBType);

				if (m_tempJDBCPoolDBType[2].contains("embedded")){
					return true;
				}
				else{
					return false;
				}
			}
		}
		catch (IOException e){
			message = "File with Functional Alias name not found. Please enter details manually if the JDBC component exist and you want to migrate.";
			return false;
		}
	}

	/**
	 * Check for the <JDBCPool>.xml file presence and get the connection pool alias name, if present inside <JDBCPool>.xml file. 
	 * Then again check for the <connection name>.xml file presence
	 * @param absolutePathOfJDBCPool
	 * @param absolutePathOfConnectionAliasDirectory
	 * @param isPasswordHandlePrefix
	 * @param productDirectoryName
	 * @param rowIndex
	 */
	public static ArrayList<String> getJDBCDBParameters(JDBCPoolName jdbcPoolName){
		
		String m_installDir = Inputs.getSourceInstallationDirectory();
		
		String absolutePathOfJDBCPool = "/IntegrationServer/config/jdbc/function/"+ jdbcPoolName.toString() +".xml";
		String absolutePathOfConnectionAliasDirectory = "/IntegrationServer/config/jdbc/pool/";
		String isPasswordHandlePrefix = "wm.is.admin.jdbc.";
		String productDirectoryName = "IntegrationServer";
		String m_searchConnPoolString = "connPoolAlias\">";
	
		String message="";
		String m_connectionPoolAliasName = "";
		String m_JDBCPoolFileRelativePath = m_installDir + absolutePathOfJDBCPool;
		ArrayList<String> dbNameUserIdPassword = new ArrayList<String>();
		dbNameUserIdPassword.add("");
		dbNameUserIdPassword.add("");
		dbNameUserIdPassword.add("");
		
		try{
		 m_connectionPoolAliasName = JPanelPreUpgradeStepsScreen.getConnectionPoolAlias(m_JDBCPoolFileRelativePath, m_searchConnPoolString);
		 if(m_connectionPoolAliasName.equalsIgnoreCase(EMPTY_STRING)){
				message="The associated connection pool alias for this Functional Alias Name not found. Please enter details manually if the JDBC component exist and you want to migrate.";
				DialougeBoxes.showErrorDialogMultiLine(
						Main.jFrameUpgradeUtilityGUI, message,
						"Connection pool not found", JOptionPane.DEFAULT_OPTION);
			}
			else{
				String m_ConnectionAliasPoolFileRelativePath = m_installDir + absolutePathOfConnectionAliasDirectory + m_connectionPoolAliasName + ".xml";
				dbNameUserIdPassword=UpdateJDBCDBParameters.getDbNameUseridPassword(isPasswordHandlePrefix, m_connectionPoolAliasName, productDirectoryName, m_ConnectionAliasPoolFileRelativePath);
				return dbNameUserIdPassword;
			}
		}
		catch (IOException e){
			message = "File with Functional Alias name not found. Please enter details manually if the JDBC component exist and you want to migrate.";
			DialougeBoxes.showErrorDialogMultiLine(
					Main.jFrameUpgradeUtilityGUI, message,
					"Functional Alias File not found", JOptionPane.DEFAULT_OPTION);
		}
		return dbNameUserIdPassword;
	}
	
	/**
	 * Returns Fail or Pass depending on the tablespace details "for data" and "for index"
	 * are empty or not. 
	 * @return - The result of pass or fail
	 */
	private String verifyTableSpaceNonEmptyStatus() {
		
		if (jPanelJDBCPools.isVisible()) {
			for (int rowNumber = 0; rowNumber < jTableJDBCPools.getRowCount(); rowNumber++) {
				if ((((ImageIcon) jTableJDBCPools.getValueAt(rowNumber, 9))
						.getImage()).equals(passIcon.getImage())) {
					if(Inputs.isRDBMSOracle()){
						if ((((String)jTableJDBCPools.getValueAt(rowNumber,
								7))).equals("")) {
							return "FAIL";
						}
						if ((((String)jTableJDBCPools.getValueAt(rowNumber,
								8))).equals("")) {
							return "FAIL";
						}
					}
				}
			}
		}

		return "PASS";
	}
	
	/**
	 * Returns true if every connection status is pass in both the optimize and
	 * jdbc pools tables
	 * @return - The result of pass or fail
	 */
	private String verifyTablesPassStatus() {
		if (jPanelJDBCPools.isVisible()) {
			for (int rowNumber = 0; rowNumber < jTableJDBCPools.getRowCount(); rowNumber++) {
				if ((((ImageIcon) jTableJDBCPools.getValueAt(rowNumber, 9))
						.getImage()).equals(failIcon.getImage())) {
					return FAIL;
				}
			}
		}
		// remove the message for warning popup message to warningMessage()
		return PASS; //$NON-NLS-1$
	}
	
	private boolean continueWhenWarningsFound(){
		boolean warningFound = false;
		if (jPanelJDBCPools.isVisible()) {
			for (int rowNumber = 0; rowNumber < jTableJDBCPools.getRowCount(); rowNumber++) {
				if ((((ImageIcon) jTableJDBCPools.getValueAt(rowNumber, 9))
						.getImage()).equals(warningIcon.getImage())) {
					warningFound = true;
				}
			}
		}
		
		if (warningFound == true) {
			String[] jdbcPoolNamesWithWarnings = new String[10];
			String[] productNamesWithWarnings = new String[10];
			String[] jdbcPoolNamesOfISWithWarnings = new String[10];
			String[] jdbcPoolNamesOfPEWithWarnings = new String[10];
			String[] jdbcPoolNamesOfOptimizeWithWarnings = new String[10];
			String[][] jdbcPoolNamesWithWarningsInSeqWithProds = new String[10][10];

			/*
			 * For generating the warning message. The message format is as
			 * follows:
			 * User has selected <product name1>,<product name2> but has not
			 * provided jdbc pool details for (<pool names for product 1>),
			 * (<pool names for product 2>).DB migrations for these jdbc pools
			 * will fail. If you are not using <pool names>, you can continue
			 * <product name1>,<product name2> migration without (<pool names
			 * for product 1>), (<pool names for product 2>) db migration.
			 * Otherwise please provide the valid jdbc pool detials for these
			 * pools and then continue. Do you want to continue without
			 * providing jdbc pool detials for these pools?
			 */
			for (int rowNumber = 0, i = 0; rowNumber < jTableJDBCPools
					.getRowCount(); rowNumber++) {
				if ((((ImageIcon) jTableJDBCPools.getValueAt(rowNumber, 9))
						.getImage()).equals(warningIcon.getImage())) {
					jdbcPoolNamesWithWarnings[i++] = (String) jTableJDBCPools
							.getValueAt(rowNumber, 0);
				}
			}
			boolean ISEntered = false, PEEntered = false, OptimizeEntered = false;
			for (int i = 0, j = 0, k = 0, l = 0, m = 0; i < jdbcPoolNamesWithWarnings.length && (jdbcPoolNamesWithWarnings[i]!=null); i++) {
				if (jdbcPoolNamesWithWarnings[i].equalsIgnoreCase(POOL_NAMES_DOCUMENT_HISTORY)) { //$NON-NLS-1$
					if (!ISEntered) {
						productNamesWithWarnings[j++] = "Integration Server";
						ISEntered = true;
					}
					jdbcPoolNamesOfISWithWarnings[k++] = "DocumentHistory";
				} else if (jdbcPoolNamesWithWarnings[i].equalsIgnoreCase("Xref")) {
					if (!ISEntered) {
						productNamesWithWarnings[j++] = "Integration Server";
						ISEntered = true;
					}
					jdbcPoolNamesOfISWithWarnings[k++] = "Xref";
				} else if (jdbcPoolNamesWithWarnings[i].equalsIgnoreCase("Archiving")) {
					if (!PEEntered) {
						productNamesWithWarnings[j++] = "Process Engine";
						PEEntered = true;
					}
					jdbcPoolNamesOfPEWithWarnings[l++] = "Archiving";
				} else if (jdbcPoolNamesWithWarnings[i].equalsIgnoreCase("ProcessAudit") ){
					if (!OptimizeEntered) {
						productNamesWithWarnings[j++] = "Optimize";
						OptimizeEntered = true;
					}
					jdbcPoolNamesOfOptimizeWithWarnings[m++] = "ProcessAudit";
				} else if (jdbcPoolNamesWithWarnings[i].equalsIgnoreCase("Reporting") ){
					if (!OptimizeEntered) {
						productNamesWithWarnings[j++] = "Optimize";
						OptimizeEntered = true;
					}
					jdbcPoolNamesOfOptimizeWithWarnings[m++] = "Reporting";
				} else if (jdbcPoolNamesWithWarnings[i].equalsIgnoreCase("Staging")) {
					if (!OptimizeEntered) {
						productNamesWithWarnings[j++] = "Optimize";
						OptimizeEntered = true;
					}
					jdbcPoolNamesOfOptimizeWithWarnings[m++] = "Staging";
				}
			}

			for (int i = 0; i < productNamesWithWarnings.length; i++) {
				if (productNamesWithWarnings[i] != null) {
					if (productNamesWithWarnings[i]
							.equalsIgnoreCase("Integration Server")) {
						for (int j = 0; j < jdbcPoolNamesOfISWithWarnings.length; j++) {
							if (jdbcPoolNamesOfISWithWarnings[j] != null) {
								jdbcPoolNamesWithWarningsInSeqWithProds[i][j] = jdbcPoolNamesOfISWithWarnings[j];
							}
						}
					} else if (productNamesWithWarnings[i]
							.equalsIgnoreCase("Process Engine")) {
						for (int j = 0; j < jdbcPoolNamesOfPEWithWarnings.length; j++) {
							if (jdbcPoolNamesOfPEWithWarnings[j] != null) {
								jdbcPoolNamesWithWarningsInSeqWithProds[i][j] = jdbcPoolNamesOfPEWithWarnings[j];
							}
						}
					} else if (productNamesWithWarnings[i].equalsIgnoreCase("Optimize")) {
						for (int j = 0; j < jdbcPoolNamesOfOptimizeWithWarnings.length; j++) {
							if (jdbcPoolNamesOfOptimizeWithWarnings[j] != null) {
								jdbcPoolNamesWithWarningsInSeqWithProds[i][j] = jdbcPoolNamesOfOptimizeWithWarnings[j];
							}
						}
					}
				}
			}

			String warningPopupMessage = WARNING_POPUP_MESSAGE_1; //$NON-NLS-1$

			for (int i = 0; i < productNamesWithWarnings.length; i++) {
				if (productNamesWithWarnings[i] != null) {
					warningPopupMessage += (i + 1) + FULLSTOP_FOLLOWEDBY_SPACE; //$NON-NLS-1$
					warningPopupMessage += productNamesWithWarnings[i] + NEXTLINE; //$NON-NLS-1$
					warningPopupMessage += WARNING_POPUP_MESSAGE_2; //$NON-NLS-1$

					if (jdbcPoolNamesWithWarningsInSeqWithProds[i][0] != null) {
						for (int j = 0; j < 10; j++) {
							if (jdbcPoolNamesWithWarningsInSeqWithProds[i][j] != null) {
								warningPopupMessage += jdbcPoolNamesWithWarningsInSeqWithProds[i][j];
								int k = j;
								if (jdbcPoolNamesWithWarningsInSeqWithProds[i][++k] != null) {
									warningPopupMessage += COMMA_FOLLOWEDBY_SPACE; //$NON-NLS-1$
								}
							}
						}
						warningPopupMessage += NEXTLINE; //$NON-NLS-1$
					}
				}
			}

			warningPopupMessage += WARNING_POPUP_MESSAGE_3; //$NON-NLS-1$

			for (int i = 0; i < productNamesWithWarnings.length; i++) {
				if (productNamesWithWarnings[i] != null) {
					warningPopupMessage += productNamesWithWarnings[i];
					if (productNamesWithWarnings[i + 1] != null) {
						warningPopupMessage += COMMA_FOLLOWEDBY_SPACE; //$NON-NLS-1$
					}
				}
			}

			warningPopupMessage += WARNING_POPUP_MESSAGE_4; //$NON-NLS-1$

			for (int i = 0; i < jdbcPoolNamesWithWarnings.length; i++) {
				if (jdbcPoolNamesWithWarnings[i] != null) {
					warningPopupMessage += jdbcPoolNamesWithWarnings[i];
					if (jdbcPoolNamesWithWarnings[i + 1] != null) {
						warningPopupMessage += COMMA_FOLLOWEDBY_SPACE; //$NON-NLS-1$
					}
				}
			}

			warningPopupMessage += WARNING_POPUP_MESSAGE_5; 

			int NonMandatoryPopupChoice = DialougeBoxes.showConfirmDialogMultiLine(
					Main.jPanelDatabaseDetailsScreen, warningPopupMessage,
					POP_UP_TITLE_WARNING, JOptionPane.YES_NO_OPTION); //$NON-NLS-1$
			if(NonMandatoryPopupChoice == JOptionPane.YES_OPTION){
				return true;
			}
			else {
				return false;
			}
			/*if (NonMandatoryPopupChoice == JOptionPane.NO_OPTION) {
				return POP_UP_TITLE_WARNING; //$NON-NLS-1$
			}*/
		}
		return true;
	}
	/**
	 * get the table status for setting the next button for enabling
	 * @return - boolean - true if the table status is pass and next button can be enabled.
	 */
	private boolean verifyTablesStatus() {
		String verifyTablesPassStatus = verifyTablesPassStatus();
		String verifyTableSpaceNonEmptyStatus = verifyTableSpaceNonEmptyStatus();
		if (verifyTablesPassStatus.equalsIgnoreCase("PASS") && verifyTableSpaceNonEmptyStatus.equalsIgnoreCase("PASS")) {
			saveTableValuesToaFile();
			return true;
		} else if (verifyTablesPassStatus.equalsIgnoreCase("FAIL")) {
			String message = "The database components for which you have provided the details, must show connected status before you can continue.";
			DialougeBoxes.showErrorDialogMultiLine(
					Main.jFrameUpgradeUtilityGUI, message,
					"Incorrect database details", JOptionPane.DEFAULT_OPTION);
			return false;
		} else if (verifyTableSpaceNonEmptyStatus.equalsIgnoreCase("FAIL")) {
			String message = "The Table space values for data and for index cannot be empty. Please contact Database Administrator to get details of the tablespace used for selected component.";
			DialougeBoxes.showErrorDialogMultiLine(
					Main.jFrameUpgradeUtilityGUI, message,
					"Incorrect tablespace details", JOptionPane.DEFAULT_OPTION);
			return false;
		} 
		else if (verifyTablesPassStatus.equalsIgnoreCase("WARNING")){
			return false;
		}
		return true;
	}
	
	/**
	 * Load values from csv file to UI
	 */
	private void loadTableValuesFromFile(){

		TableContents[] tableContents = new TableContents[20];
		tableContents = ParseCSVFile.CSVToArray(dbTablesContents);
		
		if(jTableJDBCPools.isVisible()){
			int rowCount = jTableJDBCPools.getModel().getRowCount();
			int columnCount = jTableJDBCPools.getModel().getColumnCount();
			
			for (int i = 0; i < rowCount; i++) {
				String jdbcPoolName = (String) jTableJDBCPools.getModel().getValueAt(i, 0);
				for (int j = 0; (j < tableContents.length) && (tableContents[j]!=null); j++) {
					String jdbcPoolOrComponentNameFromFile = tableContents[j].jdbcPoolName;
					if(jdbcPoolName.equals(jdbcPoolOrComponentNameFromFile)){
						jTableJDBCPools.getModel().setValueAt(tableContents[j].databaseType, i, 1);
						jTableJDBCPools.getModel().setValueAt(tableContents[j].action, i, 2);
						jTableJDBCPools.getModel().setValueAt(tableContents[j].dbURL, i, 3);
						jTableJDBCPools.getModel().setValueAt(tableContents[j].uName, i, 4);
						jTableJDBCPools.getModel().setValueAt(tableContents[j].password, i, 5);
						jTableJDBCPools.getModel().setValueAt(tableContents[j].useDefaultTableSpace, i, 6);
						jTableJDBCPools.getModel().setValueAt(tableContents[j].tableSpaceForData, i, 7);
						jTableJDBCPools.getModel().setValueAt(tableContents[j].tableSpaceForIndex, i, 8);
					}
				}
			}
		}
	}
	
	/**
	 * save values from UI to a csv file
	 */
	private void saveTableValuesToaFile(){
		int rowCount = jTableJDBCPools.getModel().getRowCount();
		
		TableContents[] tableContents = new TableContents[50];
		int i = 0;
		if(jTableJDBCPools.isVisible()){
			for (; i < rowCount; i++) {
				tableContents[i] = new TableContents();
				tableContents[i].jdbcPoolName = (String) jTableJDBCPools.getModel().getValueAt(i, 0);
				tableContents[i].databaseType = (String) jTableJDBCPools.getModel().getValueAt(i, 1);
				tableContents[i].action = (String) jTableJDBCPools.getModel().getValueAt(i, 2);
				tableContents[i].dbURL = (String) jTableJDBCPools.getModel().getValueAt(i, 3);	
				tableContents[i].uName = (String) jTableJDBCPools.getModel().getValueAt(i, 4);	
				tableContents[i].password = (String) jTableJDBCPools.getModel().getValueAt(i, 5);
				tableContents[i].useDefaultTableSpace = (Boolean) jTableJDBCPools.getModel().getValueAt(i, 6);	
				tableContents[i].tableSpaceForData = (String) jTableJDBCPools.getModel().getValueAt(i, 7);	
				tableContents[i].tableSpaceForIndex = (String) jTableJDBCPools.getModel().getValueAt(i, 8);	
			}
		}
		
		FileWriter fw;
		try {
			fw = new FileWriter(dbTablesContents);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int j=0; j< i; j++){
				bw.write(tableContents[j].jdbcPoolName + "," + tableContents[j].databaseType +"," +tableContents[j].action +","+tableContents[j].dbURL + "," + tableContents[j].uName + "," + tableContents[j].password + "," + tableContents[j].useDefaultTableSpace + "," +tableContents[j].tableSpaceForData + "," + tableContents[j].tableSpaceForIndex);
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Enable next button
	 */
	private void enableNextButton(){
		jButtonNext.setEnabled(true);
	}
	
	/**
	 * Disable next button
	 */
	private void disableNextButton(){
		jButtonNext.setEnabled(false);
	}

	/**
	 * Write the database details to the Input variables of Database details screen panel from 
	 * both the tables.
	 */
	public void writeDBDetailsToInputVariables() {
		ResetInputVariables.writeEmptyStringsToInputs();

		String dbURL = "";
		String dbUserName = "";
		String dbPasswordString = "";
		String dbForDataString = "";
		String dbForIndexString = "";
		String dbType = "";
		String delimeterdb = ":";
		String createOrMigrate;
		String createOrMigrateValue = "";
		String databaseType = "";
		
			if (jPanelJDBCPools.isVisible()) {
				for (int rowNumber = 0; rowNumber < jTableJDBCPools.getRowCount(); rowNumber++) {
					if (!(((ImageIcon) jTableJDBCPools.getValueAt(rowNumber, 9)).getImage()).equals(warningIcon.getImage())) {
					
						for (int columnNumber = 1; columnNumber <= 8; columnNumber++) {
							if (jTableJDBCPools.getColumnName(columnNumber).trim()
									.equals(TABLE_JDBC_POOLS_COLUMN_HEADINGS_DATABASE_URL)) { //$NON-NLS-1$
								dbURL = (String) jTableJDBCPools.getValueAt(rowNumber,
										columnNumber);
								String[] tempdbURLArray = dbURL.split(delimeterdb);
								dbType = tempdbURLArray[2];
							} else if (jTableJDBCPools.getColumnName(columnNumber)
									.trim().equals(TABLE_JDBC_POOLS_COLUMN_HEADINGS_USERNAME)) { //$NON-NLS-1$
								dbUserName = (String) jTableJDBCPools.getValueAt(
										rowNumber, columnNumber);
							} else if (jTableJDBCPools.getColumnName(columnNumber)
									.trim().equals(TABLE_JDBC_POOLS_COLUMN_HEADINGS_PASSWORD)) { //$NON-NLS-1$
								dbPasswordString = (String) jTableJDBCPools.getValueAt(
										rowNumber, columnNumber);
							} else if (jTableJDBCPools.getColumnName(columnNumber)
									.trim().equals(TABLE_JDBC_POOLS_COLUMN_HEADINGS_FOR_DATA)) { //$NON-NLS-1$
								dbForDataString = (String) jTableJDBCPools.getValueAt(
										rowNumber, columnNumber);
							} else if (jTableJDBCPools.getColumnName(columnNumber)
									.trim().equals(TABLE_JDBC_POOLS_COLUMN_HEADINGS_FOR_INDEX)) { //$NON-NLS-1$
								dbForIndexString = (String) jTableJDBCPools.getValueAt(
										rowNumber, columnNumber);
							}
							//-----------------------
							else if (jTableJDBCPools
									.getColumnName(columnNumber).trim()
									.equals(J_CHECKBOX_DATABASE_UPGRADE_DATABASE_ACTION)) { //$NON-NLS-1$
								createOrMigrate = (String) jTableJDBCPools.getValueAt(rowNumber, columnNumber);
								if(createOrMigrate.equalsIgnoreCase("CREATE")){
									createOrMigrateValue="C";
								}
								if(createOrMigrate.equalsIgnoreCase("MIGRATE")){
									createOrMigrateValue="M";
								}
							}
							else if (jTableJDBCPools.getColumnName(columnNumber)
									.trim().equals(TABLE_JDBC_POOLS_COLUMN_HEADINGS_DB_TYPE)){
								databaseType = (String) jTableJDBCPools.getValueAt(rowNumber, columnNumber);
							}
						/*	else if (jTableJDBCPools
									.getColumnName(columnNumber).trim()
									.equals(J_CHECKBOX_DATABASE_UPGRADE_DATABASE_ACTION)) { //$NON-NLS-1$
								createOrMigrate = (String) jTableJDBCPools.getValueAt(rowNumber, columnNumber);
								
							}*/
							//-------------------------
						}
	
						String jdbcPoolString = (String) jTableJDBCPools.getValueAt(rowNumber, 0);
						writeJBDCPoolDetailsToInputVariables(jdbcPoolString, dbURL, dbType, dbUserName, dbPasswordString, dbForDataString, dbForIndexString, createOrMigrateValue, databaseType);
					}//if
				}// for
			}
		
	}// writeDBDetailsToUserPropFile()
	
	/**
	 * To write the JDBC Pool details to the Input variables of Database details screen panel.
	 * @param jdbcPoolString - the JDBC pool name whose values are being written.
	 * @param dbURL
	 * @param dbUserName
	 * @param dbPasswordString
	 * @param dbForDataString
	 * @param dbForIndexString
	 */
	public static void writeJBDCPoolDetailsToInputVariables(String jdbcPoolString, String dbURL, String dbType, String dbUserName, String dbPasswordString, String dbForDataString, String dbForIndexString, String createOrMigrateValue, String databaseType){
		DatabaseComponentName jdbcPoolName;
		jdbcPoolName = DatabaseComponentName.valueOf(jdbcPoolString.toUpperCase());
		
		switch (jdbcPoolName) {
		//Change it to analysis and process tracker 
		case ANALYSIS:
			Inputs.setAnalysisDbUrl(dbURL);
			Inputs.setAnalysisDbUsername(dbUserName);
			Inputs.setAnalysisDbPassword(dbPasswordString);
			Inputs.setAnalysisDbTableSpaceData(dbForDataString);
			Inputs.setAnalysisDbTableSpaceIndex(dbForIndexString);
			Inputs.setAnalysisDbType(dbType);
			Inputs.setAnalysisDatabaseUpgradeType(createOrMigrateValue);
			break;
		
		case PROCESSTRACKER:
			Inputs.setProcessTrackerDbUrl(dbURL);
			Inputs.setProcessTrackerDbUsername(dbUserName);
			Inputs.setProcessTrackerDbPassword(dbPasswordString);
			Inputs.setProcessTrackerDbTableSpaceData(dbForDataString);
			Inputs.setProcessTrackerDbTableSpaceIndex(dbForIndexString);
			Inputs.setProcessTrackerDbType(dbType);
			Inputs.setProcessTrackerDatabaseUpgradeType(createOrMigrateValue);
			break;
			
		case DATAPURGE:
			Inputs.setDataPurgeDbUrl(dbURL);
			Inputs.setDataPurgeDbUsername(dbUserName);
			Inputs.setDataPurgeDbPassword(dbPasswordString);
			Inputs.setDataPurgeDbTableSpaceData(dbForDataString);
			Inputs.setDataPurgeDbTableSpaceIndex(dbForIndexString);
			Inputs.setDataPurgeDbType(dbType);
			Inputs.setDataPurgeDatabaseUpgradeType(createOrMigrateValue);
			break;
		
		case CENTRALCONFIGURATION:
			Inputs.setCentralConfigurationDbUrl(dbURL);
			Inputs.setCentralConfigurationDbUsername(dbUserName);
			Inputs.setCentralConfigurationDbPassword(dbPasswordString);
			Inputs.setCentralConfigurationTableSpaceData(dbForDataString);
			Inputs.setCentralConfigurationDbTableSpaceIndex(dbForIndexString);
			Inputs.setCentralConfigurationDbType(dbType);
			Inputs.setCentralConfigurationDatabseUpgradeType(createOrMigrateValue);
			break;
			
		case MYWEBMETHODSSERVER:
			Inputs.setMWSDbUrl(dbURL);
			Inputs.setMWSDbUsername(dbUserName);
			Inputs.setMWSDbPassword(dbPasswordString);
			Inputs.setMWSDbTableSpaceData(dbForDataString);
			Inputs.setMWSDbTableSpaceIndex(dbForIndexString);
			Inputs.setMWSDbType(dbType);
			Inputs.setMWSDatabaseUpgradeType(createOrMigrateValue);
			Inputs.setMWSDatabaseType(databaseType);
			break;
			
		case ARCHIVING:
			Inputs.setArchivingDbUrl(dbURL);
			Inputs.setArchivingDbUsername(dbUserName);
			Inputs.setArchivingDbPassword(dbPasswordString);
			Inputs.setArchivingDbTableSpaceData(dbForDataString);
			Inputs.setArchivingDbTableSpaceIndex(dbForIndexString);
			Inputs.setArchivingDbType(dbType);
			Inputs.setProcessEngineNonMandatoryArchivingDatabseUpgradeType(createOrMigrateValue);
			Inputs.setArchivingSelected();
			break;

		case DOCUMENTHISTORY:
			Inputs.setDocumentHistoryDbUrl(dbURL);
			Inputs.setDocumentHistoryDbUsername(dbUserName);
			Inputs.setDocumentHistoryDbPassword(dbPasswordString);
			Inputs.setDocumentHistoryDbTableSpaceData(dbForDataString);
			Inputs.setDocumentHistoryDbTableSpaceIndex(dbForIndexString);
			Inputs.setDocumentHistoryDbType(dbType);
			Inputs.setISCoreAuditNonMandatoryDocumentHistoryDatabseUpgradeType(createOrMigrateValue);
			Inputs.setDocumentHistory();
			break;

		case ISCOREAUDIT:
			Inputs.setIsCoreAuditDbUrl(dbURL);
			Inputs.setIsCoreAuditDbUsername(dbUserName);
			Inputs.setIsCoreAuditDbPassword(dbPasswordString);
			Inputs.setIsCoreAuditDbTableSpaceData(dbForDataString);
			Inputs.setIsCoreAuditDbTableSpaceIndex(dbForIndexString);
			Inputs.setIsCoreAuditDbType(dbType);
			Inputs.setISCoreAuditDatabaseUpgradeType(createOrMigrateValue);
			Inputs.setIsCoreAuditDatabaseType(databaseType);
			break;

		case ISINTERNAL:
			Inputs.setIsInternalDbUrl(dbURL);
			Inputs.setIsInternalDbUsername(dbUserName);
			Inputs.setIsInternalDbPassword(dbPasswordString);
			Inputs.setIsInternalDbTableSpaceData(dbForDataString);
			Inputs.setIsInternalDbTableSpaceIndex(dbForIndexString);
			Inputs.setIsInternalDbType(dbType);
			Inputs.setISInternalDatabaseUpgradeType(createOrMigrateValue);
			Inputs.setIsInternalDatabaseType(databaseType);
			break;
		
		case DISTRIBUTEDLOCKING:
			Inputs.setDistributedLockingDbUrl(dbURL);
			Inputs.setDistributedLockingDbUsername(dbUserName);
			Inputs.setDistributedLockingDbPassword(dbPasswordString);
			Inputs.setDistributedLockingDbTableSpaceData(dbForDataString);
			Inputs.setDistributedLockingDbTableSpaceIndex(dbForIndexString);
			Inputs.setDistributedLockingDbType(dbType);
			Inputs.setDistributedLockingDatabaseUpgradeType(createOrMigrateValue);
			break;

		case PROCESSAUDIT:
			Inputs.setProcessAuditDbUrl(dbURL);
			Inputs.setProcessAuditDbUsername(dbUserName);
			Inputs.setProcessAuditDbPassword(dbPasswordString);
			Inputs.setProcessAuditDbTableSpaceData(dbForDataString);
			Inputs.setProcessAuditDbTableSpaceIndex(dbForIndexString);
			Inputs.setProcessAuditDbType(dbType);
			Inputs.setProcessAuditDatabseUpgradeType(createOrMigrateValue);
			Inputs.setprocessAuditSelected();
			break;

		case PROCESSENGINE:
			Inputs.setProcessEngineDbUrl(dbURL);
			Inputs.setProcessEngineDbUsername(dbUserName);
			Inputs.setProcessEngineDbPassword(dbPasswordString);
			Inputs.setProcessEngineDbTableSpaceData(dbForDataString);
			Inputs.setProcessEngineDbTableSpaceIndex(dbForIndexString);
			Inputs.setProcessEngineDbType(dbType);
			Inputs.setProcessEngineDatabaseUpgradeType(createOrMigrateValue);
			break;

		case TRADINGNETWORKS:
			Inputs.setTNDbUrl(dbURL);
			Inputs.setTNDbUsername(dbUserName);
			Inputs.setTNDbPassword(dbPasswordString);
			Inputs.setTNDbTableSpaceData(dbForDataString);
			Inputs.setTNDbTableSpaceIndex(dbForIndexString);
			Inputs.setTNDbType(dbType);
			Inputs.setTNDatabaseUpgradeType(createOrMigrateValue);
			break;
			
		case TRADINGNETWORKSARCHIVE:
			Inputs.setTradingNetworksArchiveDbUrl(dbURL);
			Inputs.setTradingNetworksArchiveDbUsername(dbUserName);
			Inputs.setTradingNetworksArchiveDbPassword(dbPasswordString);
			Inputs.setTradingNetworksArchiveDbTableSpaceData(dbForDataString);
			Inputs.setTradingNetworksArchiveDbTableSpaceIndex(dbForIndexString);
			Inputs.setTradingNetworksArchiveDbType(dbType);
			Inputs.setTradingNetworksArchiveDatabaseUpgradeType(createOrMigrateValue);
			break;

		case CROSSREFERENCE:
			Inputs.setXRefDbUrl(dbURL);
			Inputs.setXRefDbUsername(dbUserName);
			Inputs.setXRefDbPassword(dbPasswordString);
			Inputs.setXRefDbTableSpaceData(dbForDataString);
			Inputs.setXRefDbTableSpaceIndex(dbForIndexString);
			Inputs.setXRefDbType(dbType);
			Inputs.setISCoreAuditNonMandatoryXRefDatabseUpgradeType(createOrMigrateValue);
			Inputs.setXrefSelected();
			Inputs.setXRefDatabaseType(databaseType);
			break;

		case REPORTING:
			Inputs.setReportingDbUrl(dbURL);
			Inputs.setReportingDbUsername(dbUserName);
			Inputs.setReportingDbPassword(dbPasswordString);
			Inputs.setReportingDbTableSpaceData(dbForDataString);
			Inputs.setReportingDbTableSpaceIndex(dbForIndexString);
			Inputs.setReportingDbType(dbType);
			Inputs.setReportingDatabseUpgradeType(createOrMigrateValue);
			Inputs.setReportingSelected();
			break;

		case STAGING:
			Inputs.setStagingDbUrl(dbURL);
			Inputs.setStagingDbUsername(dbUserName);
			Inputs.setStagingDbPassword(dbPasswordString);
			Inputs.setStagingDbTableSpaceData(dbForDataString);
			Inputs.setStagingDbTableSpaceIndex(dbForIndexString);
			Inputs.setStagingDbType(dbType);
			Inputs.setStagingDatabseUpgradeType(createOrMigrateValue);
			Inputs.setStagingSelected();
			break;
			
		case ONEDATAMETADATA:
			Inputs.setOneDataMetaDatadDbUrl(dbURL);
			Inputs.setOneDataMetaDataDbUsername(dbUserName);
			Inputs.setOneDataMetaDataDbPassword(dbPasswordString);
			Inputs.setOneDataMetaDataDbTableSpaceData(dbForDataString);
			Inputs.setOneDataMetaDataDbTableSpaceIndex(dbForIndexString);
			Inputs.setOneDataMetaDataDbType(dbType);
			Inputs.setOneDataMetaDataDatabseUpgradeType(createOrMigrateValue);
			Inputs.setOneDataMetaDataSelected();
			break;
			
		case ONEDATARELEASEAREA:
			Inputs.setOneDataReleaseAreadDbUrl(dbURL);
			Inputs.setOneDataReleaseAreaDbUsername(dbUserName);
			Inputs.setOneDataReleaseAreaDbPassword(dbPasswordString);
			Inputs.setOneDataReleaseAreaDbTableSpaceData(dbForDataString);
			Inputs.setOneDataReleaseAreaDbTableSpaceIndex(dbForIndexString);
			Inputs.setOneDataReleaseAreaDbType(dbType);
			Inputs.setOneDataReleaseAreaDatabseUpgradeType(createOrMigrateValue);
			Inputs.setOneDataReleaseAreaSelected();
			break;
			
		case ONEDATAWORKAREA:
			Inputs.setOneDataWorkAreaDbUrl(dbURL);
			Inputs.setOneDataWorkAreaDbUsername(dbUserName);
			Inputs.setOneDataWorkAreaDbPassword(dbPasswordString);
			Inputs.setOneDataWorkAreaDbTableSpaceData(dbForDataString);
			Inputs.setOneDataWorkAreaDbTableSpaceIndex(dbForIndexString);
			Inputs.setOneDataWorkAreaDbType(dbType);
			Inputs.setOneDataWorkAreaDatabaseUpgradeType(createOrMigrateValue);
			Inputs.setOneDataWorkAreaSelected();
			break;
			
		case ACTIVETRANSFER:
			Inputs.setActiveTransferDbUrl(dbURL);
			Inputs.setActiveTransferDbUserName(dbUserName);
			Inputs.setDbActiveTransferDbPassword(dbPasswordString);
			Inputs.setActiveTransferDbTableSpaceData(dbForDataString);
			Inputs.setActiveTransferDbTableSpaceIndex(dbForIndexString);
			Inputs.setActiveTransferDbType(dbType);
			Inputs.setActiveTransferDatabaseUpgradeType(createOrMigrateValue);
			Inputs.setActiveTransferSelected();
			
		case BUSINESSRULES:
			Inputs.setBusinessRulesDbUrl(dbURL);
			Inputs.setBusinessRulesDbUserName(dbUserName);
			Inputs.setDbBusinessRulesDbPassword(dbPasswordString);
			Inputs.setBusinessRulesDbTableSpaceData(dbForDataString);
			Inputs.setBusinessRulesDbTableSpaceIndex(dbForIndexString);
			Inputs.setBusinessRulesDbType(dbType);
			Inputs.setBusinessRulesDatabaseUpgradeType(createOrMigrateValue);
			Inputs.setBusinessRulesSelected();	

		default:
			break;
		}// switch
	}
	
	/**
     * This function is called when action is performed on Next Button.
     * It simulates the button click of TestConnection button. 
     * And if tables in this screen passes validations, it moves to next screen.
     * @param evt - the ActionEvent on Next
	 * @throws FileNotFoundException 
     */
	private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException {
		// Clicking the button test connection
		////////////////////////////////////////Remove this and take code from OSGI branch
		stopTableCellEditing();
		if(!jButtonTestConnectionSimulation()){
			return;
		}
		if (!continueWhenWarningsFound()){
			return;
		}
		
		writeDBDetailsToInputVariables();
		
		//Generate and show SummaryLog screen
		AutoUpgradeUsingUI.createAndWriteToSummaryFile();
		JPanelSummaryLogScreen.prepareSummaryPanel();
		WizardNavigation.gotoJPanelSummaryLogScreen();
	}
	
	/**
     * This function is called when action is performed on TestConnection Button.
     * @param evt - the ActionEvent on Radio button
     */
	private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {
		stopTableCellEditing();
		disableNextButton();
		LoggerClassForFullLog.loggerClassForFullLog.trace("Back button clicked, now Pre Upgrade Steps Screen will be loaded");
		WizardNavigation.gotoJPanelPreUpgradeStepsScreen();
	}

	/**
	 * 
	 * @return ALL THE DATA base component names for all the selected products depending on all the conditions.
	 */
	public static ArrayList<DatabaseComponentName>	returnDatabaseComponentNames(){
		int countDatabaseComponents = 0;
		
		ArrayList<DatabaseComponentName> databaseComponentsOfAProduct = new ArrayList<DatabaseComponentName>();
			//Integration Server database components
			if (Inputs.isIntegrationServerMigrationSelected()) { 
				
				databaseComponentsOfAProduct.add(DatabaseComponentName.ISCOREAUDIT);
				
				if(Inputs.isSourceRelease713()||Inputs.isSourceRelease80() || Inputs.isSourceRelease822() || Inputs.isSourceRelease90() || Inputs.isSourceRelease95() || Inputs.isSourceRelease96() || Inputs.isSourceRelease97() || Inputs.isSourceRelease98()){
					databaseComponentsOfAProduct.add(DatabaseComponentName.ISINTERNAL);
					databaseComponentsOfAProduct.add(DatabaseComponentName.CROSSREFERENCE);
					databaseComponentsOfAProduct.add(DatabaseComponentName.DOCUMENTHISTORY);
					
					if(!JPanelDatabaseDetailsScreen.isDatabaseEmbedded(JDBCPoolName.ISINTERNAL)){
						databaseComponentsOfAProduct.add(DatabaseComponentName.DISTRIBUTEDLOCKING);
		    		}
				}
				else if(Inputs.isSourceRelease65()) {
					databaseComponentsOfAProduct.add(DatabaseComponentName.ISINTERNAL);
					databaseComponentsOfAProduct.add(DatabaseComponentName.CROSSREFERENCE);
					if(!JPanelDatabaseDetailsScreen.isDatabaseEmbedded(JDBCPoolName.ISINTERNAL)){
						databaseComponentsOfAProduct.add(DatabaseComponentName.DISTRIBUTEDLOCKING);
		    		}
					if(Inputs.isTargetRelease80()){
						databaseComponentsOfAProduct.add(DatabaseComponentName.DOCUMENTHISTORY);
					}
				}
				else if(Inputs.isSourceRelease61()) {
					databaseComponentsOfAProduct.add(DatabaseComponentName.CROSSREFERENCE);
					databaseComponentsOfAProduct.add(DatabaseComponentName.DOCUMENTHISTORY);
				}
				else if(Inputs.isSourceRelease712()) {
					databaseComponentsOfAProduct.add(DatabaseComponentName.ISINTERNAL);
				}
			}

			//Trading networks database components
			if (Inputs.isTradingNetworksMigrationSelected()) { 
				databaseComponentsOfAProduct.add(DatabaseComponentName.TRADINGNETWORKS);
				if(!Inputs.isSourceRelease712()){
					databaseComponentsOfAProduct.add(DatabaseComponentName.TRADINGNETWORKSARCHIVE);
				}
			}

			//	Process Engine database components
			if (Inputs.isProcessEngineMigrationSelected()) {
				
				databaseComponentsOfAProduct.add(DatabaseComponentName.PROCESSENGINE);
				//Optional component for all paths
				databaseComponentsOfAProduct.add(DatabaseComponentName.PROCESSAUDIT);
				
				if( (!(Inputs.isSourceRelease65()) && Inputs.isTargetRelease80())
						|| Inputs.isTargetRelease822()|| Inputs.isTargetRelease90() || Inputs.isTargetRelease95() || Inputs.isTargetRelease96() || Inputs.isTargetRelease97() || Inputs.isTargetRelease98() || Inputs.isTargetRelease99()
						|| (Inputs.isSourceRelease713() && Inputs.isTargetRelease822() && Inputs.isUpgradeTypeOI())){
					databaseComponentsOfAProduct.add(DatabaseComponentName.ARCHIVING);
				}
				
				if(Inputs.isTargetRelease90() || Inputs.isTargetRelease95() || Inputs.isTargetRelease96() || Inputs.isTargetRelease97() || Inputs.isTargetRelease98() || Inputs.isTargetRelease99()){
					databaseComponentsOfAProduct.add(DatabaseComponentName.REPORTING);
					databaseComponentsOfAProduct.add(DatabaseComponentName.STAGING);
				}
			}

		//	MWS database components
		if(Inputs.isMyWebMethodsServerMigrationSelected())
		{
			databaseComponentsOfAProduct.add(DatabaseComponentName.MYWEBMETHODSSERVER);
		}
		
		//	Optimize database components
		if (Inputs.isOptimizeMigrationSelected()) {
			
			//Common DB components for all path migrations
			databaseComponentsOfAProduct.add(DatabaseComponentName.ANALYSIS);
			databaseComponentsOfAProduct.add(DatabaseComponentName.PROCESSTRACKER);
			
			//For all the source release starting from 713
			if(Inputs.isSourceRelease713() || Inputs.isSourceRelease80() || Inputs.isSourceRelease822() || Inputs.isSourceRelease90() || Inputs.isSourceRelease95() || Inputs.isSourceRelease96() || Inputs.isSourceRelease97() || Inputs.isSourceRelease98())
			{
				databaseComponentsOfAProduct.add(DatabaseComponentName.DATAPURGE);
			}
			
			//713 to 822
			if(Inputs.isSourceRelease713() && Inputs.isTargetRelease822()){
				databaseComponentsOfAProduct.add(DatabaseComponentName.REPORTING);
				databaseComponentsOfAProduct.add(DatabaseComponentName.STAGING);
				if(!Inputs.isProcessEngineMigrationSelected()){
					databaseComponentsOfAProduct.add(DatabaseComponentName.PROCESSAUDIT);
				}
				if(Inputs.isUpgradeTypeOI()){
					databaseComponentsOfAProduct.add(DatabaseComponentName.CENTRALCONFIGURATION);
				}
			}
			
			//80 to 822
			if(Inputs.isSourceRelease80() && Inputs.isTargetRelease822()){
					databaseComponentsOfAProduct.add(DatabaseComponentName.REPORTING);
					databaseComponentsOfAProduct.add(DatabaseComponentName.STAGING);
				//productDBComponentNames[i++] = CommonFunctions.componentCentralConfiguration;
				if(!Inputs.isProcessEngineMigrationSelected()){
					databaseComponentsOfAProduct.add(DatabaseComponentName.PROCESSAUDIT);
				}
			}
			
			//90/95/96/97/98/99 as Target
			if(Inputs.isTargetRelease90() || Inputs.isTargetRelease95() || Inputs.isTargetRelease96() || Inputs.isTargetRelease97() || Inputs.isTargetRelease98() || Inputs.isTargetRelease99()){
				if(!Inputs.isProcessEngineMigrationSelected()){
					databaseComponentsOfAProduct.add(DatabaseComponentName.PROCESSAUDIT);
				}
			}
		}
		if(Inputs.isOneDataSelected()){
			if(Inputs.isTargetRelease96() || Inputs.isTargetRelease97() || Inputs.isTargetRelease98() || Inputs.isTargetRelease99()){
				databaseComponentsOfAProduct.add(DatabaseComponentName.ONEDATAMETADATA);
				databaseComponentsOfAProduct.add(DatabaseComponentName.ONEDATAWORKAREA);
				databaseComponentsOfAProduct.add(DatabaseComponentName.ONEDATARELEASEAREA);
			}
		}
		if(Inputs.isActiveTransferMigrationSelected()){
			if(Inputs.isTargetRelease95() || Inputs.isTargetRelease96() || Inputs.isTargetRelease97() || Inputs.isTargetRelease98() || Inputs.isTargetRelease99()){
				databaseComponentsOfAProduct.add(DatabaseComponentName.ACTIVETRANSFER);
			}
			
		}
		if(Inputs.isBusinessRulesMigrationSelected()){
			if(Inputs.isTargetRelease90() || Inputs.isTargetRelease95() || Inputs.isTargetRelease96() || Inputs.isTargetRelease97() || Inputs.isTargetRelease98() || Inputs.isTargetRelease99()){
				databaseComponentsOfAProduct.add(DatabaseComponentName.BUSINESSRULES);
			}
		}
		return databaseComponentsOfAProduct;
	}

	 public void setUpComboboxColumn(TableColumn comboBoxColumn) {
		 
		ArrayList<String> databaseActions = new ArrayList<String>();
//		if(databaseComponents.equals(POOL_NAMES_ARCHIVING)
//				|| databaseComponents.equals(POOL_NAMES_DOCUMENT_HISTORY)
//				|| databaseComponents.equals(POOL_NAMES_XREF)
//				|| databaseComponents.equals(POOL_NAMES_REPORTING)
//				|| databaseComponents.equals(POOL_NAMES_STAGING) || ((!Inputs.isProcessEngineMigrationSelected()) 
//						&& databaseComponents.equals(POOL_NAMES_PROCESS_AUDIT))){
//		databaseActions.add("");
//		}
		databaseActions.add("");
		databaseActions.add("Create");
		databaseActions.add("Migrate");
		 
		//Set up the editor for the sport cells.
		JComboBox comboBox = new JComboBox(databaseActions.toArray());
		comboBoxColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		//Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		comboBoxColumn.setCellRenderer(renderer);
	}
	 
	 public void setUpDBTypeComboboxColumn(TableColumn comboBoxColumn) {
		 
		//Set up the editor for the sport cells.
			JComboBox comboBox = new JComboBox();
			comboBoxColumn.setCellEditor(new DefaultCellEditor(comboBox));
			
			//Set up tool tips for the sport cells.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			comboBoxColumn.setCellRenderer(renderer);
		}
	 
	/**
	 * This dynamically generate the JDBC pools table
	 * depending on the product selection 
	 */
	public void generateJDBCPoolsTable() {
		
		myTableModelNewJDBCPoolTable.resetTable();
//      Vector<String> tbl = new Vector<String>();
//      Vector<Object> tbl1 = new Vector<Object>();
      
//      tbl.add(tableJDBCPoolsHeadings[0]);
//      tbl.add(tableJDBCPoolsHeadings[1]);
//      tbl.add(tableJDBCPoolsHeadings[2]);
//      tbl.add(tableJDBCPoolsHeadings[3]);
//      tbl.add(tableJDBCPoolsHeadings[4]);
//      tbl.add(tableJDBCPoolsHeadings[5]);
//      tbl.add(tableJDBCPoolsHeadings[6]);
//      tbl.add(tableJDBCPoolsHeadings[7]);
//      tbl.add(tableJDBCPoolsHeadings[8]);
//      myTableModelNewJDBCPoolTable.setColumnNames(tbl);
      
      ArrayList<DatabaseComponentName> databaseComponents = returnDatabaseComponentNames();
      for (int i = 0; i < databaseComponents.size(); i++) {
      	Object[] rowForAddition;
			unknownIcon.setDescription(UNKNOWN_IMAGE_ICON);
			if(compareWithMWSComponentNames(databaseComponents.get(i).toString()) && (Inputs.isSourceRelease713() && Inputs.isTargetRelease822() && Inputs.isUpgradeTypeSBS())){
				rowForAddition =	new Object[] { databaseComponents.get(i).toString(), "External RDBMS", "Create", new String(), new String(), new String(), new Boolean(false), new String(), new String(), unknownIcon};
			}
			else{
	    		JDBCPoolName jdbcPoolName = JPanelDatabaseDetailsScreen.getJDBCPoolNameForDatabaseComponent(databaseComponents.get(i));

	    		if(JPanelDatabaseDetailsScreen.compareWithDBComponentsWhichSupportEmbeddedDB(databaseComponents.get(i).toString()) && JPanelDatabaseDetailsScreen.isDatabaseEmbedded(jdbcPoolName)){
	    			rowForAddition =	new Object[] { databaseComponents.get(i).toString(), "Embedded DB", "Migrate", new String(), new String(), new String(), new Boolean(false), new String(), new String(), unknownIcon};
	    		}
	    		else{
	    			rowForAddition =	new Object[] { databaseComponents.get(i).toString(), "External RDBMS", "Migrate", new String(), new String(), new String(), new Boolean(false), new String(), new String(), unknownIcon};
	    		}
			}
      	
			Vector<Object> row = new Vector<Object>(Arrays.asList(rowForAddition));
			
			myTableModelNewJDBCPoolTable.addRow(row);
			myTableModelNewJDBCPoolTable.fireTableDataChanged();
//		myTableModelNewJDBCPoolTable.fireTableStructureChanged();
		//When all rows are added, it triggers an TableChanged Action which invokes tableChanged from the MyTableModelNew class
      }
	}
	
	/**
	 * These method will enable the jpaneldb 
	 * @param evt 
	 */
	 private void jRadioButtonDataBaseUpgradeCloneActionPerformed(java.awt.event.ActionEvent evt) {
		 
		showJPanelRDBMS();
		 
		//this is for setting the value of radio button for Existing and Clone
		if(jRadioButtonTargetDatabaseClone.isSelected()){
			 Inputs.setDBMigrateOption("C");
		}
		else{
			Inputs.setDBMigrateOption("E");
		}
	 }
	 
	 /**
	 * @param evt 
	 * These method will enable the jpaneldb 
	 */

	 private void jRadioButtonDataBaseUpgradeExistingActionPerformed(java.awt.event.ActionEvent evt) {
		 hideJPanelRDBMS();
		 String m_delimeterDBType = ":";
		 //these is for setting the value of radio button for Existing and Clone
		if(jRadioButtonTargetDatabaseExisting.isSelected()){
			 Inputs.setDBMigrateOption("E");
		}
		else{
			//Clone
			Inputs.setDBMigrateOption("C");
		}
		
		String m_tempJDBCPoolDBType="";
		boolean flag=true;
		ArrayList<DatabaseComponentName> databaseComponents = JPanelDatabaseDetailsScreen.returnDatabaseComponentNames();
        for (int i = 0; i < databaseComponents.size(); i++) {
        	ArrayList<String> databaseURLUsernamePassword= new ArrayList<String>();
    		if(Inputs.isDBUpgradeOptionExisting() && !(compareWithOptimizeComponentNames(databaseComponents.get(i).toString()))){
    			if((compareWithOneDataComponentNames(databaseComponents.get(i).toString()))){
    				String oneDataComponentName = databaseComponents.get(i).toString();
    				String dbComponent = "";
    				if(oneDataComponentName.equalsIgnoreCase("ONEDATAMETADATA")){
    					dbComponent = "onedatamd";
    				}
    				else if(oneDataComponentName.equalsIgnoreCase("ONEDATAWORKAREA")){
    					dbComponent = "onedatawa";
    				}
    				else if(oneDataComponentName.equalsIgnoreCase("ONEDATARELEASEAREA")){
    					dbComponent = "onedatara";
    				}
    				try {
						databaseURLUsernamePassword = OneDataExtractDbDetailFromFile.extractDBUrlUserNamePassword(dbComponent);
//						System.out.println("Call method end");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
    			}
    			else{
    				JDBCPoolName jdbcPoolName = getJDBCPoolNameForDatabaseComponent(databaseComponents.get(i));
    				databaseURLUsernamePassword = JPanelDatabaseDetailsScreen.getJDBCDBParametersWithOutPopUps(jdbcPoolName);
    			}
    		}
    		else {
    			databaseURLUsernamePassword.add("");
    			databaseURLUsernamePassword.add("");
    			databaseURLUsernamePassword.add("");
    		}
        	
    		//validating RDBMS is common for all the DB components
        	if(!(databaseURLUsernamePassword.get(0).toString().equalsIgnoreCase(EMPTY_STRING))){
	        		try{
		        		String[] m_tempJDBCPoolDBTypes = databaseURLUsernamePassword.get(0).toString().split(m_delimeterDBType);
		        		if(flag){
		        			m_tempJDBCPoolDBType = m_tempJDBCPoolDBTypes[2];
		        			flag=false;
		        		}
		        		else{
		        			if(m_tempJDBCPoolDBType.equalsIgnoreCase(m_tempJDBCPoolDBTypes[2])){
		        				if (m_tempJDBCPoolDBType.equalsIgnoreCase("sqlserver")) {
		        					Inputs.setRDBMS(SQLSERVER_RDBMS);
								}
		        				else{
		        					Inputs.setRDBMS(m_tempJDBCPoolDBType.toUpperCase());
		        				}
		        			}
		        			else{
		        				String message="The RDBMSs being used by the products are not same. The utility supports a particular RDBMS at a time.";
		        				DialougeBoxes.showErrorDialogMultiLine(
		        						Main.jFrameUpgradeUtilityGUI, message,
		        						"Single RDBMS is supported", JOptionPane.DEFAULT_OPTION);
		        				return;
		        			}
		        		}
	        	}
        		catch(ArrayIndexOutOfBoundsException AIOOBE){
        			System.out.println("Database URL for the component name " + databaseComponents.get(i).toString() + "is invalid");
        		}
        }
		}
		
		//showing and generating the DB table
		generateJDBCPoolsTable();
		showJPanelDBDetils();
	 }
	 
	/**
	 * Stop the table cell editing if the either of the table is in editing mode.
	 */
	private void stopTableCellEditing() {
		if (jTableJDBCPools.isEditing()) {
			jTableJDBCPools.getCellEditor().stopCellEditing();
		}
	}

	/**
     * This function is called when action is performed on status button of a row 
     * in "Status" column of jTableJDBCPools.
     * It reads the description of the ImageIcon button and displays it in 
     * respective (error/info/warning) dialog box.
     * @param evt - the ActionEvent on button in status column.
     */
	public void jButtonLastColumnjTableJDBCPoolsActionPerformed(
			java.awt.event.ActionEvent evt) {

		for (int rowNumber = 0; rowNumber < jTableJDBCPools.getRowCount(); rowNumber++) {
			if (jTableJDBCPools.isRowSelected(rowNumber)) {
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getValueAt(rowNumber, 9);
				String popUpMessage = returnedImage.getDescription();
				if ((((ImageIcon) jTableJDBCPools.getValueAt(rowNumber, 9))
						.getImage()).equals(failIcon.getImage())) {
					DialougeBoxes.showErrorDialog(
							Main.jPanelDatabaseDetailsScreen, popUpMessage,
							POP_UP_TITLE_CONNECTION_STATUS, JOptionPane.DEFAULT_OPTION); //$NON-NLS-1$
				} else if ((((ImageIcon) jTableJDBCPools.getValueAt(rowNumber,
						9)).getImage()).equals(warningIcon.getImage())) {
					DialougeBoxes.showWarningDialog(
							Main.jPanelDatabaseDetailsScreen, popUpMessage,
							POP_UP_TITLE_CONNECTION_STATUS, JOptionPane.DEFAULT_OPTION); //$NON-NLS-1$
				} else if ((((ImageIcon) jTableJDBCPools.getValueAt(rowNumber,
						9)).getImage()).equals(passIcon.getImage()) || (((ImageIcon) jTableJDBCPools.getValueAt(rowNumber,
								9)).getImage()).equals(unknownIcon.getImage())) {
					DialougeBoxes.showInfoDialog(
							Main.jPanelDatabaseDetailsScreen, popUpMessage,
							POP_UP_TITLE_CONNECTION_STATUS, JOptionPane.DEFAULT_OPTION); //$NON-NLS-1$
				}
			}
		}
	}
	
	public void showJPanelDBDetils(){
		jPanelDB.setVisible(true);
		jTextAreaAboutTableSpaceDescription.setVisible(true);
		setJCheckBoxLoadAndSave();
	}
	
	public void hideJPanelDBDetails(){
		jPanelDB.setVisible(false);
		jTextAreaAboutTableSpaceDescription.setVisible(false);
	}
	
	/**
	 * To set the values for the buttons(ImageIcons) in the status column of 
	 * jTableJDBCPools.
	 */
	private void testConnectionForjTableJDBCPools(){
		String dbURL = null;
		String dbUserName = null;
		String dbPasswordString = null;

		//Test connection for the jTableJDBCPools
		for (int rowNumber = 0; rowNumber < jTableJDBCPools.getRowCount(); rowNumber++) {
			String errorMessage = null;
			String passMessage = null;
			String warningMessage = null;
			ImageIcon passIcon = new ImageIcon(passIconURL);
			ImageIcon failIcon = new ImageIcon(failIconURL);
			ImageIcon warningIcon = new ImageIcon(warningIconURL);
			for (int columnNumber = 3; columnNumber <= 5; columnNumber++) {
				if (jTableJDBCPools.getColumnName(columnNumber).trim()
						.equals(TABLE_JDBC_POOLS_COLUMN_HEADINGS_DATABASE_URL)) { //$NON-NLS-1$
					dbURL = (String) jTableJDBCPools.getValueAt(rowNumber,
							columnNumber);
				} else if (jTableJDBCPools.getColumnName(columnNumber).trim()
						.equals(TABLE_JDBC_POOLS_COLUMN_HEADINGS_USERNAME)) { //$NON-NLS-1$
					dbUserName = (String) jTableJDBCPools.getValueAt(rowNumber,
							columnNumber);
				} else if (jTableJDBCPools.getColumnName(columnNumber).trim()
						.equals(TABLE_JDBC_POOLS_COLUMN_HEADINGS_PASSWORD)) { //$NON-NLS-1$
					dbPasswordString = (String) jTableJDBCPools.getValueAt(
							rowNumber, columnNumber);
				}
			}
			
			String jdbcPoolName = (String) jTableJDBCPools.getValueAt(rowNumber, 0);
			//This if is required to set a warning icon to the status, if the jdbc pool is non mandatory
			if ((jdbcPoolName.equalsIgnoreCase(POOL_NAMES_ARCHIVING)
					|| jdbcPoolName.equalsIgnoreCase(POOL_NAMES_DOCUMENT_HISTORY)
					|| jdbcPoolName.equalsIgnoreCase(POOL_NAMES_XREF)
					|| jdbcPoolName.equalsIgnoreCase(POOL_NAMES_REPORTING)
					|| jdbcPoolName.equalsIgnoreCase(POOL_NAMES_STAGING) || ((!Inputs.isProcessEngineMigrationSelected()) 
							&& jdbcPoolName.equalsIgnoreCase(POOL_NAMES_PROCESS_AUDIT)))
					&& (dbURL.isEmpty() && dbPasswordString.isEmpty() && dbUserName
							.isEmpty())) {
				warningMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.EmptyURLUsernamePassword", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(warningIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(warningMessage);
				
				//This if is required to set a error icon to the status
			}
			else if ((dbURL.isEmpty() && dbPasswordString.isEmpty() && dbUserName.isEmpty())) {
				errorMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.EmptyURLUsernamePassword", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(failIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(errorMessage);
			} else if ((dbURL.isEmpty() && dbPasswordString.isEmpty())) {
				errorMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.EmptyURLPassword", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(failIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(errorMessage);
			} else if ((dbPasswordString.isEmpty() && dbUserName.isEmpty())) {
				errorMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.EmptyUsernamePassword", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(failIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(errorMessage);
			} else if ((dbURL.isEmpty() && dbUserName.isEmpty())) {
				errorMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.EmptyURLUsername", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(failIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(errorMessage);
			} else if (dbURL.isEmpty()) {
				errorMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.EmptyURL", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(failIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(errorMessage);
			} else if (dbPasswordString.isEmpty()) {
				errorMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.EmptyPassword", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(failIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(errorMessage);
			} else if (dbUserName.isEmpty()) {
				errorMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.EmptyUsername", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(failIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(errorMessage);
			} else if (!verifyDBURLFormat(dbURL)) {
				errorMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.InvalidURL", jdbcPoolName, jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(failIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(errorMessage);
			} else {
				try {
					getConnection(dbURL, dbUserName, dbPasswordString);
					passMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.ConnectionPass", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
					jTableJDBCPools.setValueAt(passIcon, rowNumber, 9);
					ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
							.getModel().getValueAt(rowNumber, 9);
					returnedImage.setDescription(passMessage);
				} catch (SQLException e1) {
					errorMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.ConnectionFail", jdbcPoolName, e1.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
					jTableJDBCPools.setValueAt(failIcon, rowNumber, 9);
					ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
							.getModel().getValueAt(rowNumber, 9);
					returnedImage.setDescription(errorMessage);
				}
			}
			if(((jdbcPoolName.equalsIgnoreCase(POOL_NAMES_IS_INTERNAL) || jdbcPoolName.equalsIgnoreCase(POOL_NAMES_MWS) || jdbcPoolName.equalsIgnoreCase(POOL_NAMES_XREF)) && ((String) jTableJDBCPools.getModel().getValueAt(rowNumber, 1)).equalsIgnoreCase("Embedded DB"))
					||
					(jdbcPoolName.equalsIgnoreCase(POOL_NAMES_IS_CORE_AUDIT) && ((String) jTableJDBCPools.getModel().getValueAt(rowNumber, 1)).equalsIgnoreCase("FlatFile")))
			{
				//set it to warning for time being
				warningMessage = I18n.get("JPanelDatabaseDetailsScreen.jTableJDBCPools.EmbeddedDBOrFlatFile", jdbcPoolName); //$NON-NLS-1$ //$NON-NLS-2$
				jTableJDBCPools.setValueAt(warningIcon, rowNumber, 9);
				ImageIcon returnedImage = (ImageIcon) jTableJDBCPools
						.getModel().getValueAt(rowNumber, 9);
				returnedImage.setDescription(warningMessage);
			}
		}
	}

	 /**
     * This function is called when action is performed on TestConnection button.
     * It calls the methods to set the descriptions to the status column buttons(Image Icons),
     * which will be displayed when button is clicked, showing as a pop-up.
     * @param evt - the ActionEvent on TestConnection Button
     */
	private void jButtonTestConnectionActionPerformed(java.awt.event.ActionEvent evt) {
		jButtonTestConnectionSimulation();
	}
		
	private boolean jButtonTestConnectionSimulation(){
		// For stopping the editing when the Test connection button is clicked
		stopTableCellEditing();
		
		testConnectionForjTableJDBCPools();
		
		if(!verifyTablesStatus()){
			disableNextButton();
			return false;
		}
		else{
			enableNextButton();
		}
		
		return true;
	}
	
	private void jCheckBoxLoadAndSaveActionPerformed(java.awt.event.ActionEvent evt){
		stopTableCellEditing();
		if(jCheckBoxLoadAndSave.isSelected()){
				loadTableValuesFromFile();
		}
	}
	
	public void setJCheckBoxLoadAndSave(){
		if (dbTablesContents.exists()){
			jCheckBoxLoadAndSave.setEnabled(true);
			jCheckBoxLoadAndSave.setSelected(false);
		}
		else{
			jCheckBoxLoadAndSave.setEnabled(false);
			jCheckBoxLoadAndSave.setSelected(false);
		}
	}

	/**
	 * Verifies the format of the database URL entered, comparing 
	 * it with the regular expression of valid URL format.
	 * @param dbURL - the DB URL to be verified. 
	 * @return - true if DB URL format is valid. 
	 */
	public static boolean verifyDBURLFormat(String dbURL) {
		String regexDBURL = "^(jdbc:wm:)(?i)(oracle|sqlserver)://[a-zA-Z0-9_.-]{1,}:[0-9]{1,};(?i)(databaseName|serviceName|SID)=[a-zA-Z0-9_.-]{1,}";
		if (IsMatch(dbURL, regexDBURL)) {
			return true;
		}
		return false;
	}

	/**
	 * Compares a regular expression pattern of a String with 
	 * that of the actual string.
	 * @param s - Actual string that need to be verified
	 * @param pattern - The Regular expression pattern
	 * @return - true if String satsfies the regualr expression pattern
	 */
	public static boolean IsMatch(String s, String pattern) {
		try {
			Pattern patt = Pattern.compile(pattern);
			Matcher matcher = patt.matcher(s);
			return matcher.matches();
		} catch (PatternSyntaxException pe) {
			pe.printStackTrace();
			return false;
		}
	}

	/**
	 * Creates connection and returns the connection from database URL,username and password. 
	 * @param URL - database URL
	 * @param userName - database username
	 * @param password - database password
	 * @return - returns the Connection created using the URL, username and password
	 * @throws SQLException - throws the exception containing the valid message 
	 * 							why the establishing connection failed.
	 */
	public static Connection getConnection(String URL, String userName,
			String password) throws SQLException {
		String oracleDirver = "com.wm.dd.jdbc.oracle.OracleDriver";
		String sqlserverDirver = "com.wm.dd.jdbc.sqlserver.SQLServerDriver";
		String oracleDatabaseType = "oracle";
		String sqlserverDatabaseType = "sqlserver";
		
		Connection conn = null;
		String delimeterdb = ":"; //$NON-NLS-1$
		String[] tempdbURLArray = URL.split(delimeterdb);
		String dbType = tempdbURLArray[2];

		try {

			if (dbType.equals(oracleDatabaseType)) { //$NON-NLS-1$
				conn = createConnection(oracleDirver, //$NON-NLS-1$
						URL, userName, password);
			} else if (dbType.equals(sqlserverDatabaseType)) { //$NON-NLS-1$
				conn = createConnection(
						sqlserverDirver, URL, //$NON-NLS-1$
						userName, password);
			}
			/*
			 * else if (context.getDBMSType().equals(DBMSType.SYBASE)) conn =
			 * createConnection("com.wm.dd.jdbc.sybase.SybaseDriver",
			 * URL,userName, password); else if
			 * (context.getDBMSType().equals(DBMSType.DB2UDB) ||
			 * context.getDBMSType().equals(DBMSType.DB2AUX)) conn =
			 * createConnection("com.wm.dd.jdbc.db2.DB2Driver", URL, userName,
			 * password);
			 */
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return conn;
	}

	/**
	 * Creates a database connection loading the correct driver 
	 * depending on the database type
	 * @param driver - database type (oracle , sqlserver, etc )
	 * @param url - database URL
	 * @param name - database username
	 * @param password - database password
	 * @return - returns the Connection created using the driver, URL, username and password
	 * @throws SQLException - throws the exception containing the valid message 
	 * 							why the establishing connection failed.
	 */
	public static Connection createConnection(String driver, String url,
			String name, String password) throws SQLException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
			loggerClassForconsole.loggerForConsole.info("Error in creating connection");
			throw new RuntimeException(ex);
		}
		Connection con = DriverManager.getConnection(url, name, password);
		unlockConnection(con);
		return con;
	}
	
	private static void unlockConnection(Connection con) {
		try {
			Class className = Class
					.forName("com.merant.datadirect.jdbc.extensions.ExtEmbeddedConnection");
			Method meth = className.getMethod("unlock",
					new Class[] { String.class });
			meth.invoke(con, new Object[] { "webMethods" });
		} catch (Exception ex) {
			System.out.println("Error in creating connection");
			throw new RuntimeException(ex);
		}
	}

	 /**
     * This function is called when action is performed on Cancel button in JpanelDatabaseDetailScreen.
     * It closes the window if yes is selected in the Close confirmation pop-up
     * @param evt - the ActionEvent on Cancel button
     */
	private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {
		int selectedOption = DialougeBoxes.closeConfirmationPopUp();
		if (selectedOption == 0) {
			Main.jFrameUpgradeUtilityGUI.dispose();
		}
	}
	
	public static JDBCPoolName getJDBCPoolNameForDatabaseComponent(DatabaseComponentName databaseComponent){
		JDBCPoolName jdbcPoolName;
		if(databaseComponent.equals(DatabaseComponentName.TRADINGNETWORKS) || databaseComponent.equals(DatabaseComponentName.TRADINGNETWORKSARCHIVE)){
			jdbcPoolName=JDBCPoolName.TN;
		}
		else if(databaseComponent.equals(DatabaseComponentName.MYWEBMETHODSSERVER) || databaseComponent.equals(DatabaseComponentName.CENTRALCONFIGURATION)){
			jdbcPoolName=JDBCPoolName.CENTRALUSERS;
		}
		else if(databaseComponent.equals(DatabaseComponentName.CROSSREFERENCE)){
			jdbcPoolName=JDBCPoolName.XREF;
		}
		else if(databaseComponent.equals(DatabaseComponentName.DISTRIBUTEDLOCKING)){
			jdbcPoolName=JDBCPoolName.ISINTERNAL;
		}
		else{
			jdbcPoolName=(JDBCPoolName)JDBCPoolName.valueOf(databaseComponent.toString());
		}
		return jdbcPoolName;
	}
	
	public static boolean compareWithOptimizeComponentNames(String dataBaseComponentName)
	{
		String[] productDBComponentNames = new String[10]; int i=0;
		String [] optimizeComponents = DatabaseMigration.getDBComponentsForOptimize(productDBComponentNames, i);
		return compareStringArrayContentsWithString(optimizeComponents, dataBaseComponentName);
	}
	
	public static boolean compareWithOneDataComponentNames(String dataBaseComponentName)
	{
		String[] productDBComponentNames = new String[10]; int i=0;
		String [] oneDataComponents = DatabaseMigration.getDBComponentsForproductNameOneData(productDBComponentNames, i);
		return compareStringArrayContentsWithString(oneDataComponents, dataBaseComponentName);
	}
	
	public static boolean compareWithMWSComponentNames(String dataBaseComponentName)
	{
		String[] productDBComponentNames = new String[10]; int i=0;
		String [] mwsComponents = DatabaseMigration.getDBComponentsForMWS(productDBComponentNames, i);
		return compareStringArrayContentsWithString(mwsComponents, dataBaseComponentName);
	}
	
	public static boolean compareWithDBComponentsWhichSupportEmbeddedDB(String databaseComponentName){
		ArrayList<String> dbComponentsWhichSupportEmbeddedDB = DatabaseMigration.getDBComponentsWhichSupportEmbeddedDB();
//		String[] dbComponentsWhichSupportEmbeddedDBArray = (String[])dbComponentsWhichSupportEmbeddedDB.toArray();
		return compareStringArrayContentsWithString(dbComponentsWhichSupportEmbeddedDB, databaseComponentName);
	}
	
	public static boolean compareWithDBComponentsWhichSupportFlatFile(String databaseComponentName){
		ArrayList<String> dbComponentsWhichSupportFlatFile = DatabaseMigration.getDBComponentsWhichSupportFlatFile();
//		String[] dbComponentsWhichSupportFlatFileArray = (String[]) dbComponentsWhichSupportFlatFile.toArray(); 
		return compareStringArrayContentsWithString(dbComponentsWhichSupportFlatFile, databaseComponentName);
	}
	
	/**
	 * Utility method to compare a string array contents with string
	 * @param stringArray
	 * @param string
	 * @return
	 */
	public static boolean compareStringArrayContentsWithString(String[] stringArray, String string){
		boolean found = false;
		for (String element : stringArray) {
		    if (string.equalsIgnoreCase(element)) {
		        found = true;
		        break;
		    }
		}
		return found;
	}
	
	public static boolean compareStringArrayContentsWithString(ArrayList<String> stringArray, String string){
		boolean found = false;
		for (String element : stringArray) {
		    if (string.equalsIgnoreCase(element)) {
		        found = true;
		        break;
		    }
		}
		return found;
	}
	
	public static ArrayList<String> getDatabaseURLUsernamePassword(String databaseType, String databaseAction, String databaseComponentNameString){
		
		ArrayList<String> databaseURLUsernamePassword = new ArrayList<String>();
		DatabaseComponentName databaseComponentName = DatabaseComponentName.valueOf(databaseComponentNameString);
		if((!(databaseType.equalsIgnoreCase("Embedded DB") || databaseType.equalsIgnoreCase("FlatFile"))) && databaseAction.equalsIgnoreCase("Migrate") &&  !(compareWithOptimizeComponentNames(databaseComponentNameString))&& ((Inputs.isUpgradeTypeSBS() && Inputs.isDBUpgradeOptionExisting()) || (Inputs.isUpgradeTypeOI()))){
			/*JDBCPoolName jdbcPoolName = getJDBCPoolNameForDatabaseComponent(databaseComponentName);
			databaseURLUsernamePassword = getJDBCDBParameters(jdbcPoolName);*/
			
			
			
			if(Inputs.isDBUpgradeOptionExisting() && !(compareWithOptimizeComponentNames(databaseComponentNameString))){
    			if((compareWithOneDataComponentNames(databaseComponentNameString))){
    				String oneDataComponentName = databaseComponentNameString;
    				String dbComponent = "";
    				if(oneDataComponentName.equalsIgnoreCase("ONEDATAMETADATA")){
    					dbComponent = "onedatamd";
    				}
    				else if(oneDataComponentName.equalsIgnoreCase("ONEDATAWORKAREA")){
    					dbComponent = "onedatawa";
    				}
    				else if(oneDataComponentName.equalsIgnoreCase("ONEDATARELEASEAREA")){
    					dbComponent = "onedatara";
    				}
    				try {
						databaseURLUsernamePassword = OneDataExtractDbDetailFromFile.extractDBUrlUserNamePassword(dbComponent);
//						System.out.println("Call method end");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			else{
    				JDBCPoolName jdbcPoolName = getJDBCPoolNameForDatabaseComponent(databaseComponentName);
    				databaseURLUsernamePassword = JPanelDatabaseDetailsScreen.getJDBCDBParametersWithOutPopUps(jdbcPoolName);
    			}
			
			
		}
		else {
			databaseURLUsernamePassword.add("");
			databaseURLUsernamePassword.add("");
			databaseURLUsernamePassword.add("");
		}
		}
		return databaseURLUsernamePassword;
	}
	
	
	
	public static ArrayList<String> getDatabaseURLUsernamePasswordWithOutPopUps( String databaseAction, String databaseComponentNameString){
		
		ArrayList<String> databaseURLUsernamePassword = new ArrayList<String>();
		DatabaseComponentName databaseComponentName = DatabaseComponentName.valueOf(databaseComponentNameString);
		if( databaseAction.equalsIgnoreCase("Migrate") && ((Inputs.isUpgradeTypeSBS() && Inputs.isDBUpgradeOptionExisting()) || Inputs.isUpgradeTypeOI()) && !(compareWithOptimizeComponentNames(databaseComponentNameString))){
			/*JDBCPoolName jdbcPoolName = getJDBCPoolNameForDatabaseComponent(databaseComponentName);
			databaseURLUsernamePassword = getJDBCDBParametersWithOutPopUps(jdbcPoolName);*/
			if(Inputs.isDBUpgradeOptionExisting() && !(compareWithOptimizeComponentNames(databaseComponentNameString))){
    			if((compareWithOneDataComponentNames(databaseComponentNameString))){
    				String oneDataComponentName = databaseComponentNameString;
    				String dbComponent = "";
    				if(oneDataComponentName.equalsIgnoreCase("ONEDATAMETADATA")){
    					dbComponent = "onedatamd";
    				}
    				else if(oneDataComponentName.equalsIgnoreCase("ONEDATAWORKAREA")){
    					dbComponent = "onedatawa";
    				}
    				else if(oneDataComponentName.equalsIgnoreCase("ONEDATARELEASEAREA")){
    					dbComponent = "onedatara";
    				}
    				try {
						databaseURLUsernamePassword = OneDataExtractDbDetailFromFile.extractDBUrlUserNamePassword(dbComponent);
//						System.out.println("Call method end");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			else{
    				JDBCPoolName jdbcPoolName = getJDBCPoolNameForDatabaseComponent(databaseComponentName);
    				databaseURLUsernamePassword = JPanelDatabaseDetailsScreen.getJDBCDBParametersWithOutPopUps(jdbcPoolName);
    			}
			
			
		}
		else {
			databaseURLUsernamePassword.add("");
			databaseURLUsernamePassword.add("");
			databaseURLUsernamePassword.add("");
		}
		}
		else {
			databaseURLUsernamePassword.add("");
			databaseURLUsernamePassword.add("");
			databaseURLUsernamePassword.add("");
		}
		return databaseURLUsernamePassword;
	}
	
	public static void hideJPanelDatabaseOriginalOrCloned(){
		jPanelDatabaseOriginalOrCloned.setVisible(false);
	}
	
	public static void showJPanelDatabaseOriginalOrCloned(){
		jPanelDatabaseOriginalOrCloned.setVisible(true);
	}

	public static void resetJPanelDatabaseOriginalOrColned(){
		buttonGroupTargetDatabase.clearSelection();
	}
	
	public static void hideJPanelRDBMS(){
		jPanelRDBMS.setVisible(false);
	}
	
	public static void showJPanelRDBMS(){
		jPanelRDBMS.setVisible(true);
		jComboBoxRDBMS.setSelectedIndex(0);
	}
	
	/**
	 * For previewing the current panel during the debugging from eclipse.
	 * @param args - No arguments are needed
	 */
	public static void main(String args[]) {
		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				JPanelDatabaseDetailsScreen jPanelDatabaseDetailsScreen;
				final String STRING_CARD = "card"; //$NON-NLS-1$
				JFrameUpgradeUtilityGUI upgradeUtilityGUI = new JFrameUpgradeUtilityGUI();
				jPanelDatabaseDetailsScreen = new JPanelDatabaseDetailsScreen();
				final Container pane = upgradeUtilityGUI.getContentPane();
				pane.add(jPanelDatabaseDetailsScreen, STRING_CARD);
				CardLayout cl = (CardLayout) (pane.getLayout());
				cl.show(pane, STRING_CARD);
				upgradeUtilityGUI.setVisible(true);
			}
		});
	}
	
	 //Methods for setting/getting the user and common.properties files
    /**
     * Calls the getCommonProperty of ProperiesFiles class
     */
    private static String getCommonProperty(String key){
    	return PropertiesFiles.getCommonProperty(key);
    }

    // Variables declaration
	private javax.swing.JTable jTableJDBCPools;
	private javax.swing.JButton jButtonBack;
	private javax.swing.JButton jButtonCancel;
	private javax.swing.JButton jButtonNext;
	private javax.swing.JButton jButtonTestConnection;
	private javax.swing.JPanel jPanelHeading;
	private javax.swing.JPanel jPanelBottomPanel;
	private javax.swing.JPanel jPanelMain;
	private javax.swing.JPanel jPanelDB;
	private javax.swing.JSeparator jSeparatorBottomPanel1;
	private javax.swing.JPanel jPanelJDBCPools;
	private javax.swing.JScrollPane jScrollPaneJDBCPoolsTable;
	private javax.swing.JTextArea jTextAreaAboutTableSpaceDescription;
	private javax.swing.JLabel jLabelOfHeadline;
	private javax.swing.JLabel jLabelOfDatabaseUpgradeType;
	private javax.swing.JLabel jLabelOfRDBMS;
	private javax.swing.JLabel jLableOfDescriptionTextOfHeadline;
	private javax.swing.JSeparator jSeparatorHeading;
	private javax.swing.JPanel jPanelBanner;
	private javax.swing.JCheckBox jCheckBoxLoadAndSave;
	
	private static javax.swing.ButtonGroup buttonGroupTargetDatabase;
	private javax.swing.JRadioButton jRadioButtonTargetDatabaseExisting;
    private javax.swing.JRadioButton jRadioButtonTargetDatabaseClone;
    private static javax.swing.JComboBox jComboBoxRDBMS;
    private static javax.swing.JPanel jPanelDatabaseOriginalOrCloned;
    private static javax.swing.JPanel jPanelRDBMS;
	// End of variables declaration
}