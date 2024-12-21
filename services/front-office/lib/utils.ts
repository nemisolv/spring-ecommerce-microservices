import { ResultCode, ResultCodeMessages } from "@/constants/api-result-code";
import { AppInfo } from "@/constants/app-info";
import ErrorResponse from "@/schemas/ErrorResponse";
import { clsx, type ClassValue } from "clsx"
import { toast } from "sonner";
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


export function showToastError(errorResponse: ErrorResponse) {
  const message = ResultCodeMessages[errorResponse.code as ResultCode];
  if (message) {
    toast.error(message);
  }
  return true;
}