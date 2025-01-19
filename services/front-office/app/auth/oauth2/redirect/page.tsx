"use client"

import { getAuthResponse } from "@/actions/auth/authenticatation"
import { getMyAuthority } from "@/actions/permission/get-authority"
import { getProfile } from "@/actions/profile/get-profile"
import { AuthContainer } from "@/components/auth/auth-container"
import { Routes } from "@/constants/routes"
import { NextPageProps } from "@/types/next-page-props"
import {  saveAuthority, saveOnlyToken, saveTokens, saveUser } from "@/util/authUtil"
import { notFound, useRouter } from "next/navigation"
import { createSearchParamsCache, parseAsString } from "nuqs/server"
import { useEffect } from "react"



const searchParamsCache = createSearchParamsCache( {
    accessToken: parseAsString.withDefault(''),
    refreshToken: parseAsString.withDefault('')
})



export default  function OAuth2RedirectPage(
    {searchParams}: NextPageProps
): React.JSX.Element {

    const router = useRouter();

    useEffect(() => {
        searchParamsCache.parse(searchParams).then(async ({ accessToken }) => {
            saveOnlyToken(accessToken);
            const response = await getAuthResponse();
            saveTokens(response.data);
    
            // allow to run in parallel -> enhance performance
            const [profile, myAuthority] = await Promise.all([getProfile(), getMyAuthority()]);
    
            console.log("🚀 ~ continueWithGoogle ~ profile::", profile);
            saveUser(profile?.data);
            saveAuthority(myAuthority?.data);
    
            router.push(Routes.Root);
        }).catch(() => {
            return notFound();
        });
    }, [router, searchParams]);


    return <AuthContainer maxWidth="sm">
        <div>Connecting to your account...</div>
    </AuthContainer>
  
}