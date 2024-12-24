import { AuthContainer } from "@/components/auth/auth-container";
import { VerifyEmailCard } from "@/components/auth/verify-email/verify-email-card";
import { createTitle } from "@/lib/utils";
import { NextPageProps } from "@/types/next-page-props";
import { Metadata } from "next";
import { createSearchParamsCache, parseAsString } from "nuqs/server";


const searchParamsCache = createSearchParamsCache({
    email: parseAsString.withDefault('')
});

export const metadata: Metadata = {
    title: createTitle('Verify Email')
  };


export default async function VerifyEmailPage({
    searchParams
}: NextPageProps) : Promise<React.JSX.Element> {
    const {email} = await searchParamsCache.parse(searchParams);
    return (
        <AuthContainer maxWidth="sm">
            <VerifyEmailCard email={email} />
        </AuthContainer>
    );
}