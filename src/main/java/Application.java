import elgamal.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application {
//    Frame
    JFrame frame;
//    Main panel
    public JPanel mainPanel;

    private JButton inputFile;
    private JButton importCipherKeyButton;
    private JTextArea log;
    private JLabel infoInputFile;
    private JLabel infoBlocksCount;
    private JLabel infoBlockSize;
    private JTextArea cipherKey;
    private JButton enterCipherKey;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton inputText;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    Model
    private JFileChooser inputChooser;
    private Key key;
    private Block[] blocks;
    private boolean canProcess = false;
    private boolean loadedFromFile = false;

    public Application(JFrame frame) {
        this.frame = frame;
        createMenu();

        DefaultCaret caret = (DefaultCaret) log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

//        Buttons
        setIcon(inputFile, "file_in.png");
        setIcon(inputText, "keyboard.png");
        setIcon(importCipherKeyButton, "cipher_key.png");
        setIcon(enterCipherKey, "keyboard.png");

        encryptButton.setEnabled(canProcess);
        decryptButton.setEnabled(canProcess);

//        Info
        infoBlockSize.setText(Constants.BLOCK_SIZE + "x" + Constants.BLOCK_SIZE);

//        Actions
        inputFile.addActionListener(e -> inputFileDialog());
        inputText.addActionListener(e -> inputTextDialog());
        enterCipherKey.addActionListener(e -> enterCipherKey());
        importCipherKeyButton.addActionListener(e -> importCipherKey());
        encryptButton.addActionListener(e -> encrypt());
        decryptButton.addActionListener(e -> decrypt());
    }

    public void inputFileDialog() {
        inputChooser = new JFileChooser();
        int returnValue = inputChooser.showOpenDialog(mainPanel);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = inputChooser.getSelectedFile().getPath();
            try {
                blocks = DataUtils.loadFile(selectedFile);
                String message = blocks.length + " data blocks have been loaded.";
                log("File " + inputChooser.getSelectedFile().getName() + " loaded.");
                log(message);
                infoInputFile.setText(inputChooser.getSelectedFile().getName());
                infoBlocksCount.setText(Integer.toString(blocks.length));
                updateButtons();
                loadedFromFile = true;
            } catch (IOException ex) {
                String message = "Could not load file: " + selectedFile;
                log(message);
                JOptionPane.showMessageDialog(frame, message, "Loading error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void inputTextDialog() {
        String input = inputTextArea.getText();
        blocks = DataUtils.loadText(input);
        log("Text has been loaded.");
        log(blocks.length + " data blocks have been loaded.");
        loadedFromFile = false;
        updateButtons();
    }

    private void enterCipherKey() {
        String keyString = JOptionPane.showInputDialog(frame, "Enter cipher key (16 characters):");
        if (keyString != null && keyString.length() == 16) {
            updateCipherKey(keyString);
            log("Cipher key added.");
        } else if(keyString != null) {
            JOptionPane.showMessageDialog(frame,"Cipher key must have exactly 16 characters.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importCipherKey() {
        JFileChooser keyChooser = new JFileChooser();
        int returnValue = keyChooser.showOpenDialog(mainPanel);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFile = keyChooser.getSelectedFile().getPath();
            try {
                byte[] bytes = DataUtils.loadBytes(selectedFile);
                if (bytes.length == 16) {
                    key = new Key(bytes);
                    updateCipherKey(new String(bytes));
                    log("Cipher key imported.");
                } else {
                    JOptionPane.showMessageDialog(frame,"Cipher key must have exactly 16 characters.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                String message = "Could not load file: " + selectedFile;
                log(message);
                JOptionPane.showMessageDialog(frame, message, "Loading error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportCipherKey() {
        if (key != null) {
            JFileChooser keyChooser = new JFileChooser();
            int returnValue = keyChooser.showSaveDialog(mainPanel);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String selectedPath = keyChooser.getSelectedFile().getAbsolutePath();
                try {
                    DataUtils.saveBytes(key.getBytes(), selectedPath);
                    log("Cipher key exported.");
                } catch (IOException ex) {
                    String message = "Could not save file: " + selectedPath;
                    log(message);
                    JOptionPane.showMessageDialog(frame, message, "Save error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame,"Key doesn't exists. Please first enter cipher key.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCipherKey(String keyString) {
        key = new Key(keyString);
        StringBuilder sb = new StringBuilder();

        for (char c : keyString.toCharArray()) {
            sb.append(String.format(String.format("0x%02X ", (byte)c)));
        }

        cipherKey.setText(sb.toString());
        updateButtons();
    }

    private void encrypt() {
        if (key != null && blocks != null) {
            canProcess = false;
            _updateButtons();

            log("Preparing encryption...");
            Encryption encryption = new Encryption(blocks, key);
            log("Starting encryption...");
            encryption.encrypt();
            log("Encryption completed successfully.");

            if (loadedFromFile) {
                JFileChooser keyChooser = new JFileChooser();
                int returnValue = keyChooser.showSaveDialog(mainPanel);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String selectedPath = keyChooser.getSelectedFile().getAbsolutePath();
                    try {
                        DataUtils.saveFile(encryption.getBlocks(), selectedPath);
                        log("Encrypted data saved: " + selectedPath);
                    } catch (IOException ex) {
                        String message = "Could not save file: " + selectedPath;
                        log(message);
                        JOptionPane.showMessageDialog(frame, message, "Save error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                outputTextArea.setText(DataUtils.saveText(encryption.getBlocks()));
            }
            canProcess = true;
            _updateButtons();
        }
    }

    private void decrypt() {
        if (key != null && blocks != null) {
            canProcess = false;
            _updateButtons();

            log("Preparing decryption...");
            Decryption decryption = new Decryption(blocks, key);
            log("Starting decryption...");
            decryption.decrypt();
            log("Decryption completed successfully.");

            if (loadedFromFile) {
                JFileChooser keyChooser = new JFileChooser();
                int returnValue = keyChooser.showSaveDialog(mainPanel);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String selectedPath = keyChooser.getSelectedFile().getAbsolutePath();
                    try {
                        DataUtils.saveFile(decryption.getBlocks(), selectedPath);
                        log("Decrypted data saved: " + selectedPath);
                    } catch (IOException ex) {
                        String message = "Could not save file: " + selectedPath;
                        log(message);
                        JOptionPane.showMessageDialog(frame, message, "Save error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                outputTextArea.setText(DataUtils.saveText(decryption.getBlocks()));
            }

            canProcess = true;
            _updateButtons();
        }
    }

    private void setIcon(JButton button, String path) {
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon imgIcon = new ImageIcon(img);
            button.setIcon(imgIcon);
        } catch (Exception ex) {
//            ...
            System.out.println(ex);
        }
    }

    private void updateButtons() {
        if (blocks != null && blocks.length > 0 && key != null) {
            canProcess = true;
        } else {
            canProcess = false;
        }
        _updateButtons();
    }

    private void _updateButtons() {
        encryptButton.setEnabled(canProcess);
        decryptButton.setEnabled(canProcess);
    }

    private void log(String message) {
        Date date = new Date();
        log.append('[' + dateFormat.format(date) + "]: " + message + '\n');
    }

    public void dispose() {}

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

//        File
        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem file_item_1 = new JMenuItem("Load file");
        file_item_1.addActionListener(e -> inputFileDialog());

        file.add(file_item_1);

//        Key
        JMenu key = new JMenu("Cipher key");
        menuBar.add(key);

        JMenuItem key_item_1 = new JMenuItem("Import key");
        key_item_1.addActionListener(e -> importCipherKey());
        JMenuItem key_item_2 = new JMenuItem("Export key");
        key_item_2.addActionListener(e -> exportCipherKey());
        JMenuItem key_item_3 = new JMenuItem("Enter key");
        key_item_3.addActionListener(e -> enterCipherKey());

        key.add(key_item_1);
        key.add(key_item_2);
        key.add(key_item_3);

        frame.setJMenuBar(menuBar);
    }
}
