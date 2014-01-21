package helpers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.Config;
import models.ReservationModel;
import models.UserModel;
import models.VehicleModel;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * User: Jasper
 * Class: This class is used to generate appropriate pdf´s for our application
 */
public class PDFHelper
{
    private Font font;  //default font
    private long unixTime; //unix time bitches
    private OutputStream file; //output stream
    private Document document; //document

    /**
     *
     * Class constructor
     *
     * @param font
     * @param fileName
     * @param directory
     * @throws Exception
     */
    public PDFHelper(Font font, String fileName, String directory) throws Exception { //constructor that catches all exceptions because java is stupid
        File dir = new File(directory);

        this.file = new FileOutputStream(new File(dir, fileName));
        this.document = new Document();

        this.font = font;
        this.unixTime = System.currentTimeMillis() / 1000L;
    }

    /**
     *
     * Open instance method, open a new pdf writer instance
     *
     * @return
     * @throws Exception
     */
    public Document openInstance() throws Exception {
        PdfWriter.getInstance(document, file);
        document.open();

        return document;
    }

    /**
     *
     * We have to close the document and file after we are done
     *
     * @throws Exception
     */
    public void closeInstance() throws Exception {
        document.close();
        file.close();
    }

    /**
     *
     * Set information for the invoice
     *
     * @param rModel
     * @param customer
     * @return
     */
    public PdfPTable setInformation(ReservationModel rModel, HashMap<String, Object> customer) {
        // a table with three columns
        PdfPTable table = new PdfPTable(2); //create table

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_COMPANY, 2));  //fill cels
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));

        if(rModel.getCompany().equals("null")) //if this person is not from a company
            table.addCell(this.cell(0, Element.ALIGN_LEFT, rModel.getName(), 1));
        else
            table.addCell(this.cell(0, Element.ALIGN_LEFT, rModel.getCompany(), 1));

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_ADDRESS, 1)); //address info
        table.addCell(this.cell(0, Element.ALIGN_LEFT, customer.get("address").toString(), 1));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_ZIP_CITY, 1));
        table.addCell(this.cell(0, Element.ALIGN_LEFT, customer.get("zip") + " " + customer.get("city"), 1));

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, "", 1)); //empty cells
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, "", 1));

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_COMPANY_CODE, 1));

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); //invoice date
        Date date = new Date();
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, "", 1));

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_TAX_CODE, 1));

        table.addCell(this.cell(0, Element.ALIGN_LEFT, "Factuurnummer: " + unixTime, 1)); //invoice number
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_ACCOUNT_NR, 1));
        table.addCell(this.cell(0, Element.ALIGN_LEFT, "Factuurdatum: " + dateFormat.format(date), 1));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_ACCOUNT_NAME, 1));

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1)); //empty bitches
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_EMAIL, 1));

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_PHONE, 1)); //contact info
        table.addCell(this.cell(0, Element.ALIGN_LEFT, "Betreft : Voertuigverhuur", 1));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_WEBSITE, 1));

        //move down
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1)); //empty shiza
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));

        return table;
    }

    /**
     *
     * Invoice table, this contains a table with the ordered product
     *
     * @param days
     * @param vModel
     * @param rModel
     * @return
     * @throws DocumentException
     */

    public PdfPTable invoiceTable(int days, VehicleModel vModel, ReservationModel rModel) throws DocumentException {

        System.out.println("dagen2: " + days);

        Font font = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.WHITE); //set font

        // a table with three columns
        PdfPTable table = new PdfPTable(4); //new table

        float[] columnWidths = new float[] {10f, 70f, 10f, 10f}; //column width
        table.setWidths(columnWidths); //set the width

        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_LEFT, "Dagen", 1, font, 5.0f, BaseColor.BLACK));  //basic bitch shit
        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_LEFT, "Omschrijving", 1, font, 5.0f, BaseColor.BLACK));
        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_LEFT, "Per dag", 1, font, 5.0f, BaseColor.BLACK));
        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_RIGHT, "Totaal", 1, font, 5.0f, BaseColor.BLACK));

        //-----------------

//        table.addCell(this.cellWithCustomFontAndPadding(0, Element.ALIGN_LEFT, "aa" + days, 1, font, 8.0f));
        table.addCell(this.cellWithCustomPadding(0, Element.ALIGN_LEFT, "" + days, 1, 8.0f));

        //row for the ordered product
        table.addCell(this.cellWithCustomPadding(0, Element.ALIGN_LEFT, vModel.getBrand() + " " + vModel.getType() + " - " + vModel.getLicenseplate() + " (" + dataTimeToString(rModel.getStartdate()) + " - " + dataTimeToString(rModel.getEnddate()) + ")", 1, 8.0f));

        DecimalFormat df = new DecimalFormat("#.##"); //format our prices

        table.addCell(this.cellWithCustomPadding(0, Element.ALIGN_LEFT, "€ " + df.format(vModel.getHourly_rent()), 1, 8.0f));
        table.addCell(this.cellWithCustomPadding(0, Element.ALIGN_RIGHT, "€ " + df.format(days * vModel.getHourly_rent()), 1, 8.0f));

        return table;
    }

    /**
     *
     * Price table for the total price
     *
     * @param price
     * @return
     * @throws DocumentException
     */

    public PdfPTable priceTable(double price) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);

        // a table with three columns
        PdfPTable table = new PdfPTable(5); //new table

        int[] columnWidths = new int[] {1, 1, 1, 1, 1}; //column width
        table.setWidths(columnWidths); //apply

        //----------------------
        this.insertEmptyCellWithCustomFontAndPadding(table, 5, font); //empty
        //----------------------

        this.insertEmptyCellWithCustomFont(table, 3, font);

        DecimalFormat df = new DecimalFormat("#.##"); //format prices

        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_RIGHT, "Totaal", 1, font));
        double taxValue = (price - (price / 1.21));

        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_RIGHT, "€ " + df.format(price - taxValue), 1, font));

        //----------------------
        this.insertEmptyCellWithCustomFont(table, 3, font);
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_RIGHT, "BTW (21%)", 1, font)); //TAX shit people don't like to pay
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_RIGHT, "€ " + df.format(taxValue), 1, font));

        //----------------------
        this.insertEmptyCellWithCustomFont(table, 5, font); //emptyyyyy
        //----------------------

        this.insertEmptyCellWithCustomFont(table, 3, font);
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_RIGHT, "Totaal inclusief", 1, font));
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_RIGHT, "€ " + df.format(price), 1, font));

        return table;
    }

    public PdfPTable defaultInformationPrepareList(ArrayList<HashMap<String, Object>> user) throws DocumentException {
        // a table with three columns
        PdfPTable table = new PdfPTable(2); //new table

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_COMPANY, 2));  //fill cels
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_ADDRESS, 2)); //address info
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_ZIP_CITY, 2));

        this.insertEmptyCellWithCustomFont(table, 2, font); //emptyyyyy

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_COMPANY_CODE, 2));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_TAX_CODE, 2));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_ACCOUNT_NR, 2));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_ACCOUNT_NAME, 2));

        this.insertEmptyCellWithCustomFont(table, 2, font); //emptyyyyy
        this.insertEmptyCellWithCustomFont(table, 2, font); //emptyyyyy

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_EMAIL, 2));

        this.insertEmptyCellWithCustomFont(table, 2, font); //emptyyyyy

        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_PHONE, 2)); //contact info
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, Config.INVOICE_WEBSITE, 2));
        table.addCell(this.cell(0, Element.ALIGN_LEFT, "Aangemaakt door: "+user.get(0).get("firstname").toString(), 2));

        //move down
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1)); //empty shiza
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));
        table.addCell(this.cell(0, Element.ALIGN_RIGHT, " ", 1));

        return table;
    }

    public PdfPTable createPrepareList(List<ReservationModel> items, DateTime date) throws DocumentException {
        Font font = null;

        // a table with three columns
        PdfPTable table = new PdfPTable(6); //new table

        int[] columnWidths = new int[] {1, 1, 1, 1, 1, 1}; //column width
        table.setWidths(columnWidths); //apply

        font = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.WHITE); //set font

        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_LEFT, "Merk", 1, font, 5.0f, BaseColor.BLACK));  //basic bitch shit
        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_LEFT, "Type", 1, font, 5.0f, BaseColor.BLACK));
        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_LEFT, "Kenteken", 1, font, 5.0f, BaseColor.BLACK));
        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_LEFT, "Afloopdatum", 1, font, 5.0f, BaseColor.BLACK));
        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_LEFT, "Naam afnemer", 1, font, 5.0f, BaseColor.BLACK));
        table.addCell(this.cellWithCustomFontAndPaddingAndBackground(0, Element.ALIGN_RIGHT, "Goedgekeurd", 1, font, 5.0f, BaseColor.BLACK));

        font = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);

        for(ReservationModel item : items) {
            table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, item.getBrand(), 1, font));
            table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, item.getType(), 1, font));
            table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, item.getLicenseplate(), 1, font));
            table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, item.getEnddate().toString("d-M-y"), 1, font));
            table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, item.getName(), 1, font));
            table.addCell(this.cellWithCustomFont(0, Element.ALIGN_RIGHT, " Ja / Nee ", 1, font));
        }

        //----------------------
        int n = 2;
        while(n-- > 0)
            this.insertEmptyCellWithCustomFont(table, 6, font); //emptyyyyy
        //----------------------

        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_RIGHT, "Aantal gereserveerde voertuigen voor "+date.toString("d-M-y")+ " is " + items.size(), 6, font));

        return table;
    }

    public PdfPTable createSignatureSpace(DateTime date) throws DocumentException {
        PdfPTable table = new PdfPTable(3); //new table

        font = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);

        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, " ", 3, font));
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, " ", 3, font));
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, " ", 3, font));
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, "Handtekening controleur", 3, font));
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, " ", 3, font));
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, " ", 3, font));
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, " ", 3, font));
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, " ", 3, font));
        table.addCell(this.cellWithCustomFont(0, Element.ALIGN_LEFT, "___________________________", 3, font));

        return table;
    }

    /**
     *
     * Convert a DateTime to a String in dutch format :D
     *
     * @param dt
     * @return
     */

    public String dataTimeToString(DateTime dt)
    {
        return dt.getDayOfMonth() + "-" + dt.getMonthOfYear() + "-" + dt.getYear();
    }


    /**
     *
     * for inserting empty cells in our table
     *
     * @param table
     * @param amount
     */

    private void insertEmptyCell(PdfPTable table, int amount)
    {
        for(int i = 0; i < amount; i++)
            table.addCell(this.cell(0, Element.ALIGN_RIGHT, "", 1));
    }

    /**
     *
     * Insert epmpty cel with custom font
     *
     * @param table
     * @param amount
     * @param font
     */

    private void insertEmptyCellWithCustomFont(PdfPTable table, int amount, Font font)
    {
        for(int i = 0; i < amount; i++)
            table.addCell(this.cellWithCustomFont(0, Element.ALIGN_RIGHT, "", 1, font));
    }

    /**
     *
     * Insert empty cell with font and padding
     *
     * @param table
     * @param amount
     * @param font
     */

    private void insertEmptyCellWithCustomFontAndPadding(PdfPTable table, int amount, Font font)
    {
        for(int i = 0; i < amount; i++)
            table.addCell(this.cellWithCustomFontAndPadding(0, Element.ALIGN_RIGHT, "", 1, font, 150.0f));
    }

    /**
     *
     * For creating basic cell
     *
     * @param bordered
     * @param alignment
     * @param contents
     * @param colspan
     * @return
     */

    private PdfPCell cell(int bordered, int alignment, String contents, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(contents, this.font));
        cell.setBorder(bordered);
        cell.setHorizontalAlignment(alignment);
        cell.setColspan(colspan);

        return cell;
    }

    /**
     *
     * Cell with custom font
     *
     * @param bordered
     * @param alignment
     * @param contents
     * @param colspan
     * @param font
     * @return
     */

    private PdfPCell cellWithCustomFont(int bordered, int alignment, String contents, int colspan, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(contents, font));
        cell.setBorder(bordered);
        cell.setHorizontalAlignment(alignment);
        cell.setColspan(colspan);

        return cell;
    }

    /**
     *
     * Cell with custom padding
     *
     * @param bordered
     * @param alignment
     * @param contents
     * @param colspan
     * @param padding
     * @return
     */

    private PdfPCell cellWithCustomPadding(int bordered, int alignment, String contents, int colspan, float padding) {
        PdfPCell cell = new PdfPCell(new Phrase(contents, this.font));
        cell.setBorder(bordered);
        cell.setPaddingTop(padding);
        cell.setHorizontalAlignment(alignment);
        cell.setColspan(colspan);

        return cell;
    }

    /**
     *
     * Custom font and padding cell
     *
     * @param bordered
     * @param alignment
     * @param contents
     * @param colspan
     * @param font
     * @param padding
     * @return
     */

    private PdfPCell cellWithCustomFontAndPadding(int bordered, int alignment, String contents, int colspan, Font font, float padding) {
        PdfPCell cell = new PdfPCell(new Phrase(contents, font));
        cell.setBorder(bordered);
        cell.setPaddingTop(padding);
        cell.setHorizontalAlignment(alignment);
        cell.setColspan(colspan);

        return cell;
    }

    /**
     *
     * Cell with custom font padding and background
     *
     * @param bordered
     * @param alignment
     * @param contents
     * @param colspan
     * @param font
     * @param padding
     * @param color
     * @return
     */
    private PdfPCell cellWithCustomFontAndPaddingAndBackground(int bordered, int alignment, String contents, int colspan, Font font, float padding, BaseColor color) { //bitches like large method names
        PdfPCell cell = new PdfPCell(new Phrase(contents, font));
        cell.setBorder(bordered);
        cell.setBackgroundColor(color);
        cell.setPaddingBottom(padding);
        cell.setHorizontalAlignment(alignment);
        cell.setColspan(colspan);

        return cell;
    }

}
