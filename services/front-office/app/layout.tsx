import type { Metadata } from "next";
import "./globals.css";

import {Inter} from 'next/font/google'
import { ModalProvider } from "@/components/providers/modal-provider";
import { ThemeProvider } from "@/components/providers/theme-provider";
import { Toaster } from '@/components/ui/sonner'
import React from "react";
import { AppInfo } from "@/constants/app-info";
import { getBaseUrl } from "@/lib/urls/get-base-url";


const inter = Inter({subsets: ['latin']})


export const metadata: Metadata = {
  metadataBase: new URL(process.env.NEXT_PUBLIC_BASE_URL!),
  title: AppInfo.APP_NAME,
  description: AppInfo.APP_DESCRIPTION,
  icons: {
    icon: '/favicon.ico',
    shortcut: '/favicon-16x16.png',
    apple: '/apple-touch-icon.png'
  },
  manifest: `${getBaseUrl()}/manifest`,
  robots: {
    index: true,
    follow: true
  }
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en"
    className="size-full min-h-screen"
    suppressHydrationWarning
    >
      <body
       className={`${inter.className} size-full`}
      >

        <ModalProvider/>
        <ThemeProvider
            attribute="class"
            defaultTheme="system"
            enableSystem
            disableTransitionOnChange
          >
            {children}

            <React.Suspense>
            <Toaster />
          </React.Suspense>
          </ThemeProvider>
      </body>
    </html>
  );
}
