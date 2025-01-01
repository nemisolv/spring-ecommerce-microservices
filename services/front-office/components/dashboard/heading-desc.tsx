

interface HeadingDescProps {
    heading: string;
    desc: string;
}

export const HeadingDesc = ({heading, desc}: HeadingDescProps) => {
    return <div>
        <h2
         className="font-bold text-2xl leading-3"
        >{heading}</h2>
        <p
         className="text-zinc-500 text-sm mt-3"
        >{desc}</p>
    </div>
}