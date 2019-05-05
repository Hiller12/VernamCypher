package bmstu.hiller.ru.VernamLab;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EncrypterVernam {

	public static final char ABC[] = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	public int MAX_TEXT_SIZE;
	private int symbolsRepeats[];
	private char inputGamma[];
	private char inputText[];

	public EncrypterVernam(String inputGammaPath, int size) throws IOException {
		MAX_TEXT_SIZE = size;
		inputGamma = new char[MAX_TEXT_SIZE];
		inputText = new char[MAX_TEXT_SIZE];
		symbolsRepeats = new int[ABC.length];
		setGamma(inputGammaPath);
	}

	private void setGamma(String gammaFilePath) throws IOException {
		int gammaCounter = 0;
		int gammaSymbol = 0;
		int ABCcounter;
		FileReader gammaReader = new FileReader(gammaFilePath);
		while ((gammaSymbol = gammaReader.read()) != -1) {
			inputGamma[gammaCounter] = (char) gammaSymbol;
			for (ABCcounter = 0; ABCcounter < ABC.length; ++ABCcounter) {
				if (ABC[ABCcounter] == inputGamma[gammaCounter]) {
					++symbolsRepeats[ABCcounter];
					break;
				}
			}
			++gammaCounter;
		}
		gammaReader.close();
	}

	private void setInputText(String inputTextFilePath) throws IOException {
		int symbolCounter = 0;
		int inputTextSymbol;
		FileReader textReader = new FileReader(inputTextFilePath);
		while ((inputTextSymbol = textReader.read()) != -1) {
			inputText[symbolCounter] = (char) inputTextSymbol;
			++symbolCounter;
		}
		textReader.close();
	}

	public char[] getGamma() {
		return inputGamma;
	}

	public char[] getInputText() {
		return inputText;
	}

	public char[] encrypt(String inputFilePath, String outputFileName) throws IOException {
		int symbolCounter = 0;
		setInputText(inputFilePath);
		FileWriter writer = new FileWriter(outputFileName, false);
		char outputText[] = new char[MAX_TEXT_SIZE];
		for (symbolCounter = 0; symbolCounter < MAX_TEXT_SIZE; ++symbolCounter) {
			outputText[symbolCounter] = ABC[((findCode(inputGamma[symbolCounter])
					+ findCode(inputText[symbolCounter])) % 26)];
			writer.append(outputText[symbolCounter]);
		}
		writer.close();
		return outputText;
	}
	
	private int findCode(char symbol) {
		for (int counter = 0; counter < ABC.length; ++counter) {
			if (symbol == ABC[counter]) {
				return counter;
			}
		}
		return -1;
	}
	
	public int[] getSymbolsRepeatsNum() {
		return symbolsRepeats;
	}
}
