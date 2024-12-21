import { AppInfo } from "@/constants/app-info";
import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}


export function createTitle(title: string, addSuffix: boolean = true): string {
  if (!addSuffix) {
    return title;
  }
  if (!title) {
    return AppInfo.APP_NAME;
  }

  return `${title} | ${AppInfo.APP_NAME}`;
}
