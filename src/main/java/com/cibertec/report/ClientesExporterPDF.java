package com.cibertec.report;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.cibertec.model.Clientes;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class ClientesExporterPDF {

	private List<Clientes> listaClientes;

	public ClientesExporterPDF(List<Clientes> listaClientes) {
		super();
		this.listaClientes = listaClientes;
	}

	private void cabeceraTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();

		celda.setBackgroundColor(new Color(50, 150, 200));
		celda.setPadding(5);

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		fuente.setStyle(Font.BOLD);

		celda.setPhrase(new Phrase("Código", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Nombres del cliente", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Dirección", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Email", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Teléfono", fuente));
		tabla.addCell(celda);
	}

	private void datosTabla(PdfPTable tabla) {

		for (Clientes cliente : listaClientes) {
			tabla.addCell(String.valueOf(cliente.getIdCliente()));
			tabla.addCell(cliente.getNombre());
			tabla.addCell(cliente.getDireccion());
			tabla.addCell(cliente.getEmail());
			tabla.addCell(cliente.getTelefono());
		}
	}

	public void exportar(HttpServletResponse response) throws DocumentException, IOException {

		Document doc = new Document(PageSize.A4);
		PdfWriter.getInstance(doc, response.getOutputStream());

		doc.open();

		Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA);
		fuenteTitulo.setColor(Color.BLUE);
		fuenteTitulo.setSize(24);

		Paragraph titulo = new Paragraph("Lista de clientes", fuenteTitulo);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		doc.add(titulo);

		PdfPTable tbl = new PdfPTable(5);
		tbl.setWidthPercentage(100);
		tbl.setSpacingBefore(20);
		tbl.setWidths(new float[] { 1, 3, 3, 3, 2 });

		cabeceraTabla(tbl);
		datosTabla(tbl);

		doc.add(tbl);
		doc.close();
	}

}
