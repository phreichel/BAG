namespace TransposeApp
{
    partial class MainForm
    {
        /// <summary>
        /// Erforderliche Designervariable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Verwendete Ressourcen bereinigen.
        /// </summary>
        /// <param name="disposing">True, wenn verwaltete Ressourcen gelöscht werden sollen; andernfalls False.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Vom Windows Form-Designer generierter Code

        /// <summary>
        /// Erforderliche Methode für die Designerunterstützung.
        /// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
        /// </summary>
        private void InitializeComponent()
        {
            this.splitText = new System.Windows.Forms.SplitContainer();
            this.fopenSource = new System.Windows.Forms.OpenFileDialog();
            this.fsaveSource = new System.Windows.Forms.SaveFileDialog();
            this.fopenTarget = new System.Windows.Forms.OpenFileDialog();
            this.fsaveTarget = new System.Windows.Forms.SaveFileDialog();
            this.btnSourceLoad = new System.Windows.Forms.Button();
            this.btnSourceSave = new System.Windows.Forms.Button();
            this.btnTargetSave = new System.Windows.Forms.Button();
            this.btnTargetLoad = new System.Windows.Forms.Button();
            this.txtSource = new System.Windows.Forms.TextBox();
            this.txtTarget = new System.Windows.Forms.TextBox();
            this.spinHalfSteps = new System.Windows.Forms.NumericUpDown();
            this.lblHalfSteps = new System.Windows.Forms.Label();
            this.btnTransform = new System.Windows.Forms.Button();
            this.targetFormat = new System.Windows.Forms.ComboBox();
            this.autoTransform = new System.Windows.Forms.CheckBox();
            ((System.ComponentModel.ISupportInitialize)(this.splitText)).BeginInit();
            this.splitText.Panel1.SuspendLayout();
            this.splitText.Panel2.SuspendLayout();
            this.splitText.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.spinHalfSteps)).BeginInit();
            this.SuspendLayout();
            // 
            // splitText
            // 
            this.splitText.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.splitText.Location = new System.Drawing.Point(1, 0);
            this.splitText.Name = "splitText";
            // 
            // splitText.Panel1
            // 
            this.splitText.Panel1.Controls.Add(this.txtSource);
            // 
            // splitText.Panel2
            // 
            this.splitText.Panel2.Controls.Add(this.txtTarget);
            this.splitText.Size = new System.Drawing.Size(799, 396);
            this.splitText.SplitterDistance = 396;
            this.splitText.TabIndex = 0;
            // 
            // fopenTarget
            // 
            this.fopenTarget.FileName = "openFileDialog2";
            // 
            // btnSourceLoad
            // 
            this.btnSourceLoad.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnSourceLoad.Location = new System.Drawing.Point(13, 402);
            this.btnSourceLoad.Name = "btnSourceLoad";
            this.btnSourceLoad.Size = new System.Drawing.Size(75, 23);
            this.btnSourceLoad.TabIndex = 1;
            this.btnSourceLoad.Text = "Load ..";
            this.btnSourceLoad.UseVisualStyleBackColor = true;
            this.btnSourceLoad.Click += new System.EventHandler(this.btnSourceLoad_Click);
            // 
            // btnSourceSave
            // 
            this.btnSourceSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnSourceSave.Location = new System.Drawing.Point(95, 402);
            this.btnSourceSave.Name = "btnSourceSave";
            this.btnSourceSave.Size = new System.Drawing.Size(75, 23);
            this.btnSourceSave.TabIndex = 2;
            this.btnSourceSave.Text = "Save ..";
            this.btnSourceSave.UseVisualStyleBackColor = true;
            this.btnSourceSave.Click += new System.EventHandler(this.btnSourceSave_Click);
            // 
            // btnTargetSave
            // 
            this.btnTargetSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnTargetSave.Location = new System.Drawing.Point(713, 402);
            this.btnTargetSave.Name = "btnTargetSave";
            this.btnTargetSave.Size = new System.Drawing.Size(75, 23);
            this.btnTargetSave.TabIndex = 3;
            this.btnTargetSave.Text = "Save ..";
            this.btnTargetSave.UseVisualStyleBackColor = true;
            this.btnTargetSave.Click += new System.EventHandler(this.btnTargetSave_Click);
            // 
            // btnTargetLoad
            // 
            this.btnTargetLoad.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnTargetLoad.Location = new System.Drawing.Point(632, 402);
            this.btnTargetLoad.Name = "btnTargetLoad";
            this.btnTargetLoad.Size = new System.Drawing.Size(75, 23);
            this.btnTargetLoad.TabIndex = 4;
            this.btnTargetLoad.Text = "Load ..";
            this.btnTargetLoad.UseVisualStyleBackColor = true;
            this.btnTargetLoad.Click += new System.EventHandler(this.btnTargetLoad_Click);
            // 
            // txtSource
            // 
            this.txtSource.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtSource.Location = new System.Drawing.Point(0, 0);
            this.txtSource.Multiline = true;
            this.txtSource.Name = "txtSource";
            this.txtSource.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.txtSource.Size = new System.Drawing.Size(396, 396);
            this.txtSource.TabIndex = 0;
            this.txtSource.TextChanged += new System.EventHandler(this.txtSource_TextChanged);
            // 
            // txtTarget
            // 
            this.txtTarget.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtTarget.Location = new System.Drawing.Point(0, 0);
            this.txtTarget.Multiline = true;
            this.txtTarget.Name = "txtTarget";
            this.txtTarget.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.txtTarget.Size = new System.Drawing.Size(399, 396);
            this.txtTarget.TabIndex = 0;
            // 
            // spinHalfSteps
            // 
            this.spinHalfSteps.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.spinHalfSteps.Location = new System.Drawing.Point(340, 402);
            this.spinHalfSteps.Maximum = new decimal(new int[] {
            7,
            0,
            0,
            0});
            this.spinHalfSteps.Minimum = new decimal(new int[] {
            7,
            0,
            0,
            -2147483648});
            this.spinHalfSteps.Name = "spinHalfSteps";
            this.spinHalfSteps.Size = new System.Drawing.Size(56, 20);
            this.spinHalfSteps.TabIndex = 5;
            this.spinHalfSteps.ValueChanged += new System.EventHandler(this.spinHalfSteps_ValueChanged);
            // 
            // lblHalfSteps
            // 
            this.lblHalfSteps.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.lblHalfSteps.AutoSize = true;
            this.lblHalfSteps.Location = new System.Drawing.Point(278, 404);
            this.lblHalfSteps.Name = "lblHalfSteps";
            this.lblHalfSteps.Size = new System.Drawing.Size(56, 13);
            this.lblHalfSteps.TabIndex = 6;
            this.lblHalfSteps.Text = "Half Steps";
            // 
            // btnTransform
            // 
            this.btnTransform.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnTransform.Location = new System.Drawing.Point(402, 399);
            this.btnTransform.Name = "btnTransform";
            this.btnTransform.Size = new System.Drawing.Size(75, 23);
            this.btnTransform.TabIndex = 7;
            this.btnTransform.Text = "Transform";
            this.btnTransform.UseVisualStyleBackColor = true;
            this.btnTransform.Click += new System.EventHandler(this.btnTransform_Click);
            // 
            // targetFormat
            // 
            this.targetFormat.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.targetFormat.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.targetFormat.FormattingEnabled = true;
            this.targetFormat.Items.AddRange(new object[] {
            "TEXT",
            "MUSICML"});
            this.targetFormat.Location = new System.Drawing.Point(483, 401);
            this.targetFormat.Name = "targetFormat";
            this.targetFormat.Size = new System.Drawing.Size(81, 21);
            this.targetFormat.TabIndex = 8;
            this.targetFormat.SelectedIndexChanged += new System.EventHandler(this.targetFormat_SelectedIndexChanged);
            // 
            // autoTransform
            // 
            this.autoTransform.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.autoTransform.AutoSize = true;
            this.autoTransform.Location = new System.Drawing.Point(402, 429);
            this.autoTransform.Name = "autoTransform";
            this.autoTransform.Size = new System.Drawing.Size(98, 17);
            this.autoTransform.TabIndex = 9;
            this.autoTransform.Text = "Auto Transform";
            this.autoTransform.UseVisualStyleBackColor = true;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.ClientSize = new System.Drawing.Size(800, 446);
            this.Controls.Add(this.autoTransform);
            this.Controls.Add(this.targetFormat);
            this.Controls.Add(this.btnTransform);
            this.Controls.Add(this.lblHalfSteps);
            this.Controls.Add(this.spinHalfSteps);
            this.Controls.Add(this.btnTargetLoad);
            this.Controls.Add(this.btnTargetSave);
            this.Controls.Add(this.btnSourceSave);
            this.Controls.Add(this.btnSourceLoad);
            this.Controls.Add(this.splitText);
            this.HelpButton = true;
            this.Name = "MainForm";
            this.ShowIcon = false;
            this.Text = "Transpose App";
            this.Load += new System.EventHandler(this.MainForm_Load);
            this.splitText.Panel1.ResumeLayout(false);
            this.splitText.Panel1.PerformLayout();
            this.splitText.Panel2.ResumeLayout(false);
            this.splitText.Panel2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.splitText)).EndInit();
            this.splitText.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.spinHalfSteps)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitText;
        private System.Windows.Forms.TextBox txtSource;
        private System.Windows.Forms.TextBox txtTarget;
        private System.Windows.Forms.OpenFileDialog fopenSource;
        private System.Windows.Forms.SaveFileDialog fsaveSource;
        private System.Windows.Forms.OpenFileDialog fopenTarget;
        private System.Windows.Forms.SaveFileDialog fsaveTarget;
        private System.Windows.Forms.Button btnSourceLoad;
        private System.Windows.Forms.Button btnSourceSave;
        private System.Windows.Forms.Button btnTargetSave;
        private System.Windows.Forms.Button btnTargetLoad;
        private System.Windows.Forms.NumericUpDown spinHalfSteps;
        private System.Windows.Forms.Label lblHalfSteps;
        private System.Windows.Forms.Button btnTransform;
        private System.Windows.Forms.ComboBox targetFormat;
        private System.Windows.Forms.CheckBox autoTransform;
    }
}

