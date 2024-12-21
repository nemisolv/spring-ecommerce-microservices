
export type Params = Record<string, string | string[] | undefined>;
export type SearchParams = {
    [key: string]: string | string[] | undefined;
}

export type NextPageProps = {
    params: Promise<Params>;
    searchParams: Promise<SearchParams>;
}