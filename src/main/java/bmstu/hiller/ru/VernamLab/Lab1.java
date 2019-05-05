package bmstu.hiller.ru.VernamLab;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;

import bmstu.hiller.ru.VernamLab.EncrypterVernam;

public class Lab1 {
	public static final int MAX_TEXT_SIZE = 200;
	public static final String[] gammaCollection = {"G1.txt", "G2.txt", "G3.txt", "G4.txt"};
	public static final String[] inTextCollection = {"text1.txt", "text2.txt"};
	public static final String[] outTextCollection = {"encryptText1.txt", "encryptText2.txt", "encryptText3.txt",
			"encryptText4.txt", "encryptText5.txt", "encryptText6.txt"};
	
	public static void main(String[] args) throws IOException {
		int counter;
		char[][] outputTexts = new char[outTextCollection.length][];
		int[][] gammaRepeats = new int[gammaCollection.length][];
		int bigramNumberText1G1Text2G2;
		int bigramNumberText1G3Text2G4;
		int bigramNumberText1G1Text1G2;
		int bigramNumberText1G3Text1G4;
		int rowBiasCur = 0;
		int rowBiasTemp = 0;
		EncrypterVernam cyphers[] = new EncrypterVernam [gammaCollection.length];
		for (counter = 0; counter < gammaCollection.length; ++counter) {
			cyphers[counter] = new EncrypterVernam(gammaCollection[counter], MAX_TEXT_SIZE);
			outputTexts[counter] = cyphers[counter].encrypt(inTextCollection[(counter + 1) % 2],
					outTextCollection[counter]);
			gammaRepeats[counter] = cyphers[counter].getSymbolsRepeatsNum();
		}
		outputTexts[4] = cyphers[1].encrypt(inTextCollection[0], outTextCollection[4]);
		outputTexts[5] = cyphers[3].encrypt(inTextCollection[0], outTextCollection[5]);
		
		bigramNumberText1G1Text2G2 = BigrammNumber(outputTexts[0], outputTexts[1]);
		bigramNumberText1G3Text2G4 = BigrammNumber(outputTexts[2], outputTexts[3]);
		bigramNumberText1G1Text1G2 = BigrammNumber(outputTexts[0], outputTexts[4]);
		bigramNumberText1G3Text1G4 = BigrammNumber(outputTexts[2], outputTexts[5]);
		
		Workbook book = new HSSFWorkbook();
		Sheet sheetLab = book.createSheet("Lab1Summary");
		Row headline = sheetLab.createRow(0);
		++rowBiasCur;
		++rowBiasTemp;
		Row[] probabilities = new Row[gammaCollection.length];
		Cell probabilitiesCells[][] = new Cell[probabilities.length]
				[bmstu.hiller.ru.VernamLab.EncrypterVernam.ABC.length + 1];
		for (counter = 0; counter < gammaCollection.length; ++counter) {
			probabilities[counter] = sheetLab.createRow(counter + rowBiasCur);
			probabilities[counter].createCell(0).setCellValue("Г"+(counter + 1));
			++rowBiasTemp;
		}
		rowBiasCur = rowBiasTemp;
		Cell headlineCells[] = new Cell[bmstu.hiller.ru.VernamLab.EncrypterVernam.ABC.length + 1];
		headlineCells[0] = headline.createCell(0);
		headlineCells[0].setCellValue("Вероятность Гамма");
		for (counter = 1; counter < bmstu.hiller.ru.VernamLab.EncrypterVernam.ABC.length + 1; ++counter) {
			headlineCells[counter] = headline.createCell(counter);
			headlineCells[counter].setCellValue("" + bmstu.hiller.ru.VernamLab.EncrypterVernam.ABC[counter - 1]);
			for (int rowCounter = 0; rowCounter < gammaCollection.length; ++rowCounter) {
				probabilitiesCells[rowCounter][counter] = probabilities[rowCounter].createCell(counter);
				probabilitiesCells[rowCounter][counter].setCellValue(
						(double)gammaRepeats[rowCounter][counter - 1] / MAX_TEXT_SIZE);
				
			}
		}
		sheetLab.autoSizeColumn(0);
		book.write(new FileOutputStream("lab1Sum.xls"));
		book.close();
		System.out.println("Количество биграмм в текстах text1G1 и text2G2 = " + bigramNumberText1G1Text2G2);
		System.out.println("Количество биграмм в текстах text1G3 и text2G4 = " + bigramNumberText1G3Text2G4);
		System.out.println("Количество биграмм в текстах text1G1 и text1G2 = " + bigramNumberText1G1Text1G2);
		System.out.println("Количество биграмм в текстах text1G3 и text1G4 = " + bigramNumberText1G3Text1G4);
		return;
	}

	private static int BigrammNumber(char[] text1, char[] text2) {
		int SymbolsCounter;
		int bigrammCounter = 0;
		for (SymbolsCounter = 0; SymbolsCounter < MAX_TEXT_SIZE; ++SymbolsCounter) {
			if (text1[SymbolsCounter] == text2[SymbolsCounter]) {
				++bigrammCounter;
			}
		}
		return bigrammCounter;
	}
}
