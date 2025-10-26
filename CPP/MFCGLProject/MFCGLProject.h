
// MFCGLProject.h: Hauptheaderdatei für die PROJECT_NAME-Anwendung
//

#pragma once

#ifndef __AFXWIN_H__
	#error "'pch.h' vor dieser Datei für PCH einschließen"
#endif

#include "resource.h"		// Hauptsymbole


// CMFCGLProjectApp:
// Siehe MFCGLProject.cpp für die Implementierung dieser Klasse
//

class CMFCGLProjectApp : public CWinApp
{
public:
	CMFCGLProjectApp();

// Überschreibungen
public:
	virtual BOOL InitInstance();

// Implementierung

	DECLARE_MESSAGE_MAP()
};

extern CMFCGLProjectApp theApp;
