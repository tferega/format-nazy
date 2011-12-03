/*
package hr.element.fn.config



object C {
  val B = Map(
    "NUL" -> ByteData(0x00, "NUL", "Null character"),
    "SOH" -> ByteData(0x01, "SOH", "Start of Header"),
    "STX" -> ByteData(0x02, "STX", "Start of Text"),
    "ETX" -> ByteData(0x03, "ETX", "End of Text"),
    "EOT" -> ByteData(0x04, "EOT", "End of Transmission"),
    "ENQ" -> ByteData(0x05, "ENQ", "Enquiry"),
    "ACK" -> ByteData(0x06, "ACK", "Acknowledgment"),
    "BEL" -> ByteData(0x07, "BEL", "Bell"),
    "BS"  -> ByteData(0x08, "BS",  "Backspace"),
    "HT"  -> ByteData(0x09, "HT",  "Horizontal Tab"),
    "LF"  -> ByteData(0x0A, "LF",  "Line feed"),
    "VT"  -> ByteData(0x0B, "VT",  "Vertical Tab"),
    "FF"  -> ByteData(0x0C, "FF",  "Form feed"),
    "CR"  -> ByteData(0x0D, "CR",  "Carriage return"),
    "SO"  -> ByteData(0x0E, "SO",  "Shift Out"),
    "SI"  -> ByteData(0x0F, "SI",  "Shift In"),
    "DLE" -> ByteData(0x10, "DLE", "Data Link Escape"),
    "DC1" -> ByteData(0x11, "DC1", "Device Control 1"),
    "DC2" -> ByteData(0x12, "DC2", "Device Control 2"),
    "DC3" -> ByteData(0x13, "DC3", "Device Control 3"),
    "DC4" -> ByteData(0x14, "DC4", "Device Control 4"),
    "NAK" -> ByteData(0x15, "NAK", "Negative Acknowledgement"),
    "SYN" -> ByteData(0x16, "SYN", "Synchronous idle"),
    "ETB" -> ByteData(0x17, "ETB", "End of Transmission Block"),
    "CAN" -> ByteData(0x18, "CAN", "Cancel"),
    "EM"  -> ByteData(0x19, "EM",  "End of Medium"),
    "SUB" -> ByteData(0x1A, "SUB", "Substitute"),
    "ESC" -> ByteData(0x1B, "ESC", "Escape"),
    "FS"  -> ByteData(0x1C, "FS",  "File Separator"),
    "GS"  -> ByteData(0x1D, "GS",  "Group Separator"),
    "RS"  -> ByteData(0x1E, "RS",  "Record Separator"),
    "US"  -> ByteData(0x1F, "US",  "Unit Separator"),
    "DEL" -> ByteData(0x7F, "DEL", "Delete"))


  val NewlineList = Map(
    "WIN" -> LineBreakData(B("CR").v ++ B("LF").v, "WIN", "Microsoft Windows"),
    "NIX" -> LineBreakData(B("LF").v, "NIX", "*NIX"),
    "MAC" -> LineBreakData(B("CR").v, "MAC", "Mac OS"))


  val AllowedNewline = Seq(NewlineList("WIN"), NewlineList("NIX"))


  val BOMList = Seq(
      BOMData(Seq(0xEF, 0xBB, 0xBF), "UTF-8"),
      BOMData(Seq(0xFE, 0xFF), "UTF-16 (BE)"),
      BOMData(Seq(0xFF, 0xFE), "UTF-16 (LE)"),
      BOMData(Seq(0x00, 0x00, 0xFE, 0xFF), "UTF-32 (BE)"),
      BOMData(Seq(0xFF, 0xFE, 0x00, 0x00), "UTF-32 (LE)"),
      BOMData(Seq(0x2B, 0x2F, 0x76, 0x38), "UTF-7"),
      BOMData(Seq(0x2B, 0x2F, 0x76, 0x39), "UTF-7"),
      BOMData(Seq(0x2B, 0x2F, 0x76, 0x2B), "UTF-7"),
      BOMData(Seq(0x2B, 0x2F, 0x76, 0x2F), "UTF-7"),
      BOMData(Seq(0xF7, 0x64, 0x4C), "UTF-1"),
      BOMData(Seq(0xDD, 0x73, 0x66, 0x73), "UTF-EBCDIC"),
      BOMData(Seq(0x0E, 0xFE, 0xFF), "SCSU"),
      BOMData(Seq(0xFB, 0xEE, 0x28), "BOCU-1"),
      BOMData(Seq(0xFB, 0xEE, 0x28, 0xFF), "BOCU-1"),
      BOMData(Seq(0x84, 0x31, 0x95, 0x33), "GB-18030"))


  val IllegalByteList = Seq(
    B("NUL"),
    B("SOH"),
    B("STX"),
    B("ETX"),
    B("EOT"),
    B("ENQ"),
    B("ACK"),
    B("BEL"),
    B("BS"),
    B("HT"),
    B("VT"),
    B("FF"),
    B("SO"),
    B("SI"),
    B("DLE"),
    B("DC1"),
    B("DC2"),
    B("DC3"),
    B("DC4"),
    B("NAK"),
    B("SYN"),
    B("ETB"),
    B("CAN"),
    B("EM"),
    B("SUB"),
    B("ESC"),
    B("FS"),
    B("GS"),
    B("RS"),
    B("US"),
    B("DEL"))
}
*/