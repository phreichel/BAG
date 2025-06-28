using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TransposeApp
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            targetFormat.SelectedIndex = 0;
        }

        private void btnSourceLoad_Click(object sender, EventArgs e)
        {
            var result = fopenSource.ShowDialog();
            if (result == DialogResult.OK && fopenSource.CheckFileExists)
            {
                var text = File.ReadAllText(fopenSource.FileName);
                txtSource.Text = text;
            }
        }

        private void btnSourceSave_Click(object sender, EventArgs e)
        {
            var result = fsaveSource.ShowDialog();
            if (result == DialogResult.OK)
            {
                var text = txtSource.Text;
                File.WriteAllText(fsaveSource.FileName, text);
            }
        }

        private void btnTargetLoad_Click(object sender, EventArgs e)
        {
            var result = fopenTarget.ShowDialog();
            if (result == DialogResult.OK && fopenTarget.CheckFileExists)
            {
                var text = File.ReadAllText(fopenTarget.FileName);
                txtTarget.Text = text;
            }
        }

        private void btnTargetSave_Click(object sender, EventArgs e)
        {
            var result = fsaveTarget.ShowDialog();
            if (result == DialogResult.OK)
            {
                var text = txtTarget.Text;
                File.WriteAllText(fsaveTarget.FileName, text);
            }
        }

        private void btnTransform_Click(object sender, EventArgs e)
        {
            convert();
        }

        private string transposeText(string notes, decimal steps)
        {
            string result = "";
            string[] lines = notes.Split('\n');
            foreach (var line in lines)
            {
                var trimmed = line.Trim();
                if (String.IsNullOrEmpty(trimmed) || trimmed.StartsWith(">"))
                {
                    result += trimmed;
                    result += "\r\n";
                }
                else
                {
                    bool first = true;
                    string[] bars = trimmed.Split('|');                    
                    foreach (var bar in bars)
                    {
                        if (first)
                        {
                            result += "\t";
                        } else
                        {
                            result += " | \t";
                        }
                        first = false;

                        string[] tones = bar.Split(' ');
                        foreach (var tone in tones)
                        {

                            decimal number = -1;

                            var cleaned = tone.Trim();
                            cleaned = cleaned.ToUpper();

                            if (!String.IsNullOrEmpty(cleaned))
                            {

                                switch (cleaned)
                                {
                                    case "C":
                                        number = 0;
                                        break;
                                    case "C#":
                                    case "CIS":
                                    case "DB":
                                    case "DES":
                                        number = 1;
                                        break;
                                    case "D":
                                        number = 2;
                                        break;
                                    case "D#":
                                    case "DIS":
                                    case "EB":
                                    case "ES":
                                        number = 3;
                                        break;
                                    case "E":
                                        number = 4;
                                        break;
                                    case "F":
                                        number = 5;
                                        break;
                                    case "F#":
                                    case "FIS":
                                    case "GB":
                                    case "GES":
                                        number = 6;
                                        break;
                                    case "G":
                                        number = 7;
                                        break;
                                    case "G#":
                                    case "GIS":
                                    case "AB":
                                    case "AS":
                                        number = 8;
                                        break;
                                    case "A":
                                        number = 9;
                                        break;
                                    case "A#":
                                    case "AIS":
                                    case "HB":
                                    case "B":
                                        number = 10;
                                        break;
                                    case "H":
                                        number = 11;
                                        break;
                                }

                                if (number == -1)
                                {
                                    result += " ?? ";
                                }
                                else
                                {
                                    number = (12 + number + steps) % 12;
                                    switch (number)
                                    {
                                        case 0: result += " C  "; break;
                                        case 1: result += " C# "; break;
                                        case 2: result += " D  "; break;
                                        case 3: result += " D# "; break;
                                        case 4: result += " E  "; break;
                                        case 5: result += " F  "; break;
                                        case 6: result += " F# "; break;
                                        case 7: result += " G  "; break;
                                        case 8: result += " G# "; break;
                                        case 9: result += " A  "; break;
                                        case 10: result += " A# "; break;
                                        case 11: result += " H  "; break;
                                    }
                                }
                            }
                        }
                    }
                    result += "\r\n";
                } 
            }
            return result;
        }


        private string transposeMusicML(string notes, decimal steps)
        {
            int nbars = 0;
            string result = @"<?xml version=""1.0"" encoding=""UTF-8""?>
<!DOCTYPE score-partwise PUBLIC ""-//Recordare//DTD MusicXML 3.1 Partwise//EN"" ""http://www.musicxml.org/dtds/partwise.dtd"">
<score-partwise version=""3.1"">
    <part-list>
    <score-part id=""P1""/>
    </part-list>
    <part id=""P1"">
";
            string[] lines = notes.Split('\n');
            foreach (var line in lines)
            {
                var trimmed = line.Trim();
                if (String.IsNullOrEmpty(trimmed) || trimmed.StartsWith(">"))
                {
                    if (trimmed.StartsWith(">"))
                    {
                        result += "<!-- " + trimmed + "-->";
                        result += "\r\n";
                    }
                }
                else
                {
                    string[] bars = trimmed.Split('|');
                    foreach (var bar in bars)
                    {

                        string[] tones = bar.Split(' ');

                        var count = 0;
                        foreach (var tone in tones)
                        {
                            var cleaned = tone.Trim();
                            cleaned = cleaned.ToUpper();
                            if (!String.IsNullOrEmpty(cleaned))
                            {
                                count++;
                            }
                        }

                        if (count > 0)
                        {

                            nbars++;

                            result += @"    <measure number=""" + nbars + @""">
    <attributes>
    <divisions>" + count + @"</divisions>
    <time>
        <beats>" + count + @"</beats>
        <beat-type>4</beat-type>
    </time>
    <clef>
        <sign>G</sign>
        <line>2</line>
    </clef>
    </attributes>
";

                            foreach (var tone in tones)
                            {

                                decimal number = -1;

                                var cleaned = tone.Trim();
                                cleaned = cleaned.ToUpper();

                                if (!String.IsNullOrEmpty(cleaned))
                                {

                                    switch (cleaned)
                                    {
                                        case "C":
                                            number = 0;
                                            break;
                                        case "C#":
                                        case "CIS":
                                        case "DB":
                                        case "DES":
                                            number = 1;
                                            break;
                                        case "D":
                                            number = 2;
                                            break;
                                        case "D#":
                                        case "DIS":
                                        case "EB":
                                        case "ES":
                                            number = 3;
                                            break;
                                        case "E":
                                            number = 4;
                                            break;
                                        case "F":
                                            number = 5;
                                            break;
                                        case "F#":
                                        case "FIS":
                                        case "GB":
                                        case "GES":
                                            number = 6;
                                            break;
                                        case "G":
                                            number = 7;
                                            break;
                                        case "G#":
                                        case "GIS":
                                        case "AB":
                                        case "AS":
                                            number = 8;
                                            break;
                                        case "A":
                                            number = 9;
                                            break;
                                        case "A#":
                                        case "AIS":
                                        case "HB":
                                        case "B":
                                            number = 10;
                                            break;
                                        case "H":
                                            number = 11;
                                            break;
                                    }

                                    if (number == -1)
                                    {
                                        // skip.
                                    }
                                    else
                                    {
                                        result += "<note><pitch>";
                                        number = (12 + number + steps) % 12;
                                        switch (number)
                                        {
                                            case 0: result += "<step>C</step>"; break;
                                            case 1: result += "<step>C</step><alter>1</alter>"; break;
                                            case 2: result += "<step>D</step>"; break;
                                            case 3: result += "<step>D</step><alter>1</alter>"; break;
                                            case 4: result += "<step>E</step>"; break;
                                            case 5: result += "<step>F</step>"; break;
                                            case 6: result += "<step>F</step><alter>1</alter>"; break;
                                            case 7: result += "<step>G</step>"; break;
                                            case 8: result += "<step>G</step><alter>1</alter>"; break;
                                            case 9: result += "<step>A</step>"; break;
                                            case 10: result += "<step>A</step><alter>1</alter>"; break;
                                            case 11: result += "<step>H</step>"; break;
                                        }
                                        result += "\r\n";
                                        result += @"<octave>4</octave>
    </pitch>
    <duration>4</duration>
    <type>quarter</type>
    </note>
";
                                    }
                                }
                            }
                        }
                        result += "</measure>";
                        result += "\r\n";
                    }
                }
            }

            result += @"
</part>
</score-partwise>
";

            return result;
        }

        private void txtSource_TextChanged(object sender, EventArgs e)
        {
            if (autoTransform.Checked)
            {
                convert();
            }
        }

        private void convert()
        {
            var source = txtSource.Text;
            var steps = spinHalfSteps.Value;
            if (targetFormat.SelectedIndex == 0)
            {
                var target = transposeText(source, steps);
                txtTarget.Text = target;
            }
            else if (targetFormat.SelectedIndex == 1)
            {
                var target = transposeMusicML(source, steps);
                txtTarget.Text = target;
            }
        }

        private void spinHalfSteps_ValueChanged(object sender, EventArgs e)
        {
            if (autoTransform.Checked)
            {
                convert();
            }
        }

        private void targetFormat_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (autoTransform.Checked)
            {
                convert();
            }
        }
    }

}
