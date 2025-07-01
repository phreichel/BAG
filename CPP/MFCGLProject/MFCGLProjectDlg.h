
// MFCGLProjectDlg.h: Headerdatei
//

#pragma once


// CMFCGLProjectDlg-Dialogfeld
class CMFCGLProjectDlg : public CDialogEx
{
// Konstruktion
public:
	CMFCGLProjectDlg(CWnd* pParent = nullptr);	// Standardkonstruktor

// Dialogfelddaten
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_MFCGLPROJECT_DIALOG };
#endif

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV-Unterstützung


// Implementierung
protected:
	HICON m_hIcon;

	// Generierte Funktionen für die Meldungstabellen
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()
public:
	CStatic mStaticGLStatus;
	afx_msg void OnBnClickedButtonGlinit();
	afx_msg void OnBnClickedButtonGltoggle();
	afx_msg void OnBnClickedButtonGlrefresh();
	CButton mButtonGLInit;
	CButton mButtonGLToggle;
	CButton mButtonGLRefresh;
};
